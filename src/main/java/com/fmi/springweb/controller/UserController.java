package com.fmi.springweb.controller;

import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        try {
            this.userService.register(model);
            return ResponseEntity.ok(new ResponseDto(true, null));
        } catch (RegistrationFailedException exception) {
            return ResponseEntity.status(HttpStatusCode.valueOf(502)).body(new ResponseDto(false, exception.getMessage()));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto> loginAccount(@RequestBody UserDto model) {
        try {
            String bearerToken = this.userService.login(model);
            return ResponseEntity.ok(new ResponseDto(true, bearerToken));
        } catch (AuthenticationFailedException exception) {
            return ResponseEntity.status(HttpStatusCode.valueOf(502)).body(new ResponseDto(false, exception.getMessage()));
        }
    }
}
