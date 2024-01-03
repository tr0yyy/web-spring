package com.fmi.springweb.dto;

public class CarSummaryDto {
    public Long carId;
    public String carBrand;

    public CarSummaryDto(Long carId, String carBrand) {
        this.carId = carId;
        this.carBrand = carBrand;
    }
}
