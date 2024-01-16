package com.fmi.springweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    public String username;
    @NotNull
    public String password;
    @Email
    public String email;

    public UserDto(){}
    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
