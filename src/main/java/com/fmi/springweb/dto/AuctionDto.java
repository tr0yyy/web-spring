package com.fmi.springweb.dto;

import java.util.Date;

public class AuctionDto {
    public Long auctionId;
    public String carModel;
    public String carBrand;
    public Long carId;
    public Date startDate;
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
