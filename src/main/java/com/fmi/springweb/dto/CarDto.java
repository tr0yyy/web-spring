package com.fmi.springweb.dto;

public class CarDto {
    public Long carId;
    public String carBrand;

    public CarDto(Long carId, String carBrand) {
        this.carId = carId;
        this.carBrand = carBrand;
    }
}
