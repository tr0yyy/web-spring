package com.fmi.springweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CarDto {
    @NotNull
    public String carModel;
    @NotNull
    public String carBrand;
    @NotNull
    public String country;
    @NotNull
    @Min(0)
    public int manufactureYear;
    @NotNull
    @Min(0)
    public int kilometers;
    @NotNull
    @Min(0)
    public float initialPrice;

    public CarDto() {
    }

    public CarDto(String carModel, String carBrand, String country, int manufactureYear, int kilometers, float initialPrice) {
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.country = country;
        this.manufactureYear = manufactureYear;
        this.kilometers = kilometers;
        this.initialPrice = initialPrice;
    }
}
