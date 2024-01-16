package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotNull;

public class CarSummaryDto {
    @NotNull
    public Long carId;
    @NotNull
    public String carBrand;

    public CarSummaryDto(Long carId, String carBrand) {
        this.carId = carId;
        this.carBrand = carBrand;
    }
}
