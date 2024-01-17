package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class PlaceBidDto {
    @NotNull
    public Long auctionId;
    @PositiveOrZero
    public Float funds;
}
