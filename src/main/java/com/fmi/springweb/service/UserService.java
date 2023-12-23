package com.fmi.springweb.service;

import com.fmi.springweb.component.JwtTokenUtil;
import com.fmi.springweb.constants.Role;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.model.OrderEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validatePassword(String specifiedPassword, UserEntity user) {
        return passwordEncoder.matches(specifiedPassword, user.getPassword());
    }

    @Transactional
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

}
