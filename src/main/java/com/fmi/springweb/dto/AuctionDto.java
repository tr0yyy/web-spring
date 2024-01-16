package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class AuctionDto {
    @NotNull
    public Long auctionId;
    @NotNull
    public String carModel;
    @NotNull
    public String carBrand;
    @NotNull
    public Long carId;
    @NotNull
    public Date startDate;
    @NotNull
    public Date endDate;

    public AuctionDto(Long auctionId, String carModel, String carBrand, Long carId, Date startDate, Date endDate) {
        this.auctionId = auctionId;
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
