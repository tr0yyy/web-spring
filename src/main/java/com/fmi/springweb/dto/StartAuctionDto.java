package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class StartAuctionDto {
    @NotNull
    public Long carId;
    @PositiveOrZero
    public Long days;
}
