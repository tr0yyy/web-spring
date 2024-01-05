package com.fmi.springweb.dto;

public class UserDto {
    public String username;
    public String password;
    public String email;

    public UserDto(){}
    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
