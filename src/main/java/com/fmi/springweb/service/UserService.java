package com.fmi.springweb.service;

import com.fmi.springweb.component.JwtTokenUtil;
import com.fmi.springweb.constants.Role;
import com.fmi.springweb.dto.CarDto;
import com.fmi.springweb.dto.UpdateAccountDto;
import com.fmi.springweb.dto.UserCarsDetailsDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.model.OrderEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CarService carService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil,
                       CarService carService) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.carService = carService;
    }

    public boolean validatePassword(String specifiedPassword, UserEntity user) {
        return passwordEncoder.matches(specifiedPassword, user.getPassword());
    }

    public void register(UserDto userDto) throws RegistrationFailedException {
        Optional<UserEntity> existingUsername = userRepository.findByUsername(userDto.username);

        if (existingUsername.isPresent()) {
            throw new RegistrationFailedException("User already registered");
        }

        UserEntity user = new UserEntity(userDto.username, passwordEncoder.encode(userDto.password), userDto.email, Role.USER);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RegistrationFailedException("Registration failed");
        }
    }

    public String login(UserDto model) throws AuthenticationFailedException {
        Optional<UserEntity> existingUser = userRepository.findByUsername(model.username);

        if (existingUser.isEmpty()) {
            throw new AuthenticationFailedException("Username provided does not exist");
        }

        if (!this.validatePassword(model.password, existingUser.get())) {
            throw new AuthenticationFailedException("Invalid password");
        }

        return jwtTokenUtil.generateToken(existingUser.get());
    }

    public UserEntity getUser(String username) throws AuthenticationFailedException {
        Optional<UserEntity> existingUser = userRepository.findByUsername(username);

        if (existingUser.isEmpty()) {
            throw new AuthenticationFailedException("Username provided does not exist");
        }

        return existingUser.get();
    }

    public void addOrderToUser(UserEntity user, OrderEntity order) {
       user.addNewOrderToUser(order);
       user.setFunds(user.getFunds() + order.getFundsAdded());
       user.setLastRecordUpdate(new Date());
       userRepository.save(user);
    }

    public void updateAccountDetails(UpdateAccountDto updateAccountDto) throws AuthenticationFailedException {
        Optional<UserEntity> existingUser =
                userRepository.findByUsername(updateAccountDto.existingUsername);

        if (existingUser.isEmpty()) {
            throw new AuthenticationFailedException("Username provided does not exist");
        }

        UserEntity foundUser = existingUser.get();

        logger.info("Updating account " + foundUser.getUsername());

        if (updateAccountDto.newUsername != null) {
            Optional<UserEntity> existingUserWithNewUsername =
                    userRepository.findByUsername(updateAccountDto.newUsername);

            if (existingUserWithNewUsername.isPresent()) {
                throw new AuthenticationFailedException("Username already registered");
            }

            logger.info("Updating username to " + updateAccountDto.newUsername);
            foundUser.setUsername(updateAccountDto.newUsername);
        }

        if (updateAccountDto.newPassword != null) {
            logger.info("Updating password");
            foundUser.setPassword(passwordEncoder.encode(updateAccountDto.newPassword));
        }

        userRepository.save(foundUser);
        logger.info("Changes saved");
    }

    public UserCarsDetailsDto getUserProfile(String username) throws AuthenticationFailedException {
        UserEntity existingUser = userRepository.findByUsername(username).orElse(null);

        if (existingUser == null) {
            throw new AuthenticationFailedException("Username provided does not exist");
        }

        logger.info("Computing user profile for " + username);

        UserCarsDetailsDto userCarsDetails = new UserCarsDetailsDto();
        userCarsDetails.username = username;
        userCarsDetails.email = existingUser.getEmail();
        userCarsDetails.funds = existingUser.getFunds();

        List<CarEntity> userCars = carService.computeCarsForUser(existingUser);

        userCarsDetails.cars = userCars != null ? userCars
                .stream()
                .map(carEntity ->
                        new CarDto(carEntity.getCarId(), carEntity.getCarModel()))
                .collect(Collectors.toList()) : new ArrayList<>();

        return userCarsDetails;
    }
}
