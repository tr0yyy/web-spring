package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.dto.UpdateAccountDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    public final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDto> registerAccount(@RequestBody UserDto model) {
        return RequestHandler.handleRequest(() -> {
            this.userService.register(model);
            return ResponseEntity.ok(new ResponseDto(true, null));
        }, RegistrationFailedException.class);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto> loginAccount(@RequestBody UserDto model) {
        return RequestHandler.handleRequest(() -> {
            String bearerToken = this.userService.login(model);
            return ResponseEntity.ok(new ResponseDto(true, bearerToken));
        }, AuthenticationFailedException.class);
    }

    @PostMapping("/core/account/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody UpdateAccountDto model) {
        return RequestHandler.handleRequest(() -> {
            this.userService.updateAccountDetails(model);
            return ResponseEntity.ok(new ResponseDto(true, "Change successful"));
        }, AuthenticationFailedException.class);
    }
}
