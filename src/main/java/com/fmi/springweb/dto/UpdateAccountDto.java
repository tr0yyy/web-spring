package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateAccountDto {
    @NotNull
    public String existingUsername;
    @NotNull
    public String newUsername;
    @NotNull
    public String newPassword;

    public UpdateAccountDto(String existingUsername, String newUsername, String newPassword) {
        this.existingUsername = existingUsername;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }
}
