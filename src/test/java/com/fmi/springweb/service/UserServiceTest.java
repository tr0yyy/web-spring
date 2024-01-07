package com.fmi.springweb.service;

import com.fmi.springweb.component.JwtTokenUtil;
import com.fmi.springweb.constants.Role;
import com.fmi.springweb.dto.UpdateAccountDto;
import com.fmi.springweb.dto.UserCarsDetailsDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.model.CarBrandEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("UnitTests")
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private CarService carService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUserSuccess() throws RegistrationFailedException {
        UserDto userDto = new UserDto("newUser", "password123", "newuser@example.com");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        userService.register(userDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        UserDto userDto = new UserDto("existingUser", "password123", "existinguser@example.com");
        UserEntity existingUser = new UserEntity("existingUser", "password123", "existinguser@example.com", Role.USER);

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        assertThrows(RegistrationFailedException.class, () -> userService.register(userDto));
    }

    @Test
    public void testLoginUserSuccess() throws AuthenticationFailedException {
        UserDto userDto = new UserDto("existingUser", "password123", null);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtTokenUtil.generateToken(userEntity)).thenReturn("ExpectedTokenValue");

        String token = userService.login(userDto);

        assertEquals("ExpectedTokenValue", token);
    }

    @Test
    public void testLoginUserInvalidUsername() {
        UserDto userDto = new UserDto("nonExistentUser", "password123", null);

        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        assertThrows(AuthenticationFailedException.class, () -> userService.login(userDto));
    }

    @Test
    public void testLoginUserInvalidPassword() {
        UserDto userDto = new UserDto("existingUser", "wrongPassword", null);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(AuthenticationFailedException.class, () -> userService.login(userDto));
    }

    @Test
    public void testUpdateUsernameSuccess() throws AuthenticationFailedException {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto("existingUser", "newUsername", null);
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.empty());

        userService.updateAccountDetails(updateAccountDto);

        verify(userRepository, times(1)).save(existingUser);
        assertEquals("newUsername", existingUser.getUsername());
    }

    @Test
    public void testUpdateUsernameAlreadyExists() {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto("existingUser", "newUsername", null);
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(AuthenticationFailedException.class,() -> userService.updateAccountDetails(updateAccountDto));
    }

    @Test
    public void testUpdatePasswordSuccess() throws AuthenticationFailedException {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto("existingUser", null, "newPassword");
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        userService.updateAccountDetails(updateAccountDto);

        verify(userRepository, times(1)).save(existingUser);
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    public void testUpdateAccountDetailsInvalidUsername() {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto("nonExistentUser", null, null);

        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        assertThrows(AuthenticationFailedException.class, () -> userService.updateAccountDetails(updateAccountDto));
    }

    @Test
    public void testGetUserProfileSuccess() throws AuthenticationFailedException {
        String username = "existingUser";
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername(username);
        existingUser.setEmail("user@example.com");
        existingUser.setFunds(100.0F);
        List<CarEntity> userCars = new ArrayList<>();
        CarBrandEntity carBrand = new CarBrandEntity(1L, "Test", "Test");
        userCars.add(new CarEntity(carBrand, "Test", 2000, 2000, 2000F));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
        when(carService.computeCarsForUser(existingUser)).thenReturn(userCars);

        UserCarsDetailsDto userDetails = userService.getUserProfile(username);

        assertEquals(username, userDetails.username);
        assertEquals("user@example.com", userDetails.email);
        assertEquals(100.0, userDetails.funds, 0.001);
        assertEquals(userCars.size(), userDetails.cars.size());
    }

    @Test
    public void testGetUserProfileInvalidUsername() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(AuthenticationFailedException.class, () -> userService.getUserProfile(username));
    }
}
