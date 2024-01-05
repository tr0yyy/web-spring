package com.fmi.springweb.dto;

public class UpdateAccountDto {
    public String existingUsername;
    public String newUsername;
    public String newPassword;

    public UpdateAccountDto(String existingUsername, String newUsername, String newPassword) {
        this.existingUsername = existingUsername;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }
}
