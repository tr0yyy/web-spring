package com.fmi.springweb.dto;

public class CarDto {
    public String carModel;
    public String carBrand;
    public String country;
    public int manufactureYear;
    public int kilometers;
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
