package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class UserCarsDetailsDto {
    @NotNull
    public String username;
    @NotNull
    public String email;
    @PositiveOrZero
    public Float funds;
    public List<CarSummaryDto> cars;

    @Override
    public String toString() {
        return "UserCarsDetailsDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", funds=" + funds +
                ", cars=" + cars +
                '}';
    }
}
