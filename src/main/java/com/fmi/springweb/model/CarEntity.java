package com.fmi.springweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name="cars")
@Getter
@Setter
public class CarEntity {
    @Id
    @GeneratedValue
    private Long carId;
    @ManyToOne
    @JoinColumn(name="brandName")
    private CarBrandEntity carBrand;
    private String carModel;
    private int manufactureYear;
    private int kilometers;
    private float initialPrice;

    public CarEntity() {}

    public CarEntity(CarBrandEntity carBrand, String carModel, int manufactureYear, int kilometers, float initialPrice) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.manufactureYear = manufactureYear;
        this.kilometers = kilometers;
        this.initialPrice = initialPrice;
    }
}
