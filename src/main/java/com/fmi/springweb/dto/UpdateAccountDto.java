package com.fmi.springweb.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class UpdateAccountDto {
    @NotNull
    public String existingUsername;
    @Nullable
    public String newUsername;
    @Nullable
    public String newPassword;

    public UpdateAccountDto(String existingUsername, @Nullable String newUsername, @Nullable String newPassword) {
        this.existingUsername = existingUsername;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }
}
