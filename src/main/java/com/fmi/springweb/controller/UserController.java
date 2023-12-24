package com.fmi.springweb.controller;

import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.dto.UpdateAccountDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.exceptions.RegistrationFailedException;
import com.fmi.springweb.service.UserService;
import org.springframework.http.HttpStatusCode;
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
        ResponseDto response;
        try {
            this.userService.register(model);
            response = new ResponseDto(true, null);
        } catch (RegistrationFailedException exception) {
            response = new ResponseDto(false, exception.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto> loginAccount(@RequestBody UserDto model) {
        ResponseDto response;
        try {
            String bearerToken = this.userService.login(model);
            response = new ResponseDto(true, bearerToken);
        } catch (AuthenticationFailedException exception) {
            response = new ResponseDto(false, exception.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/core/account/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody UpdateAccountDto model) {
        ResponseDto response;
        try {
            this.userService.updateAccountDetails(model);
            response = new ResponseDto(true, "Change successful");
        } catch (AuthenticationFailedException exception) {
            response = new ResponseDto(false, exception.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
