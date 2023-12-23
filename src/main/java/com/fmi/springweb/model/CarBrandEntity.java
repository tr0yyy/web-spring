package com.fmi.springweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="car_brands")
@Getter
@Setter
public class CarBrandEntity {
    @Id
    @GeneratedValue
    private Long brandId;
    private String brandName;
    private String country;
}
