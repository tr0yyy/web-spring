package com.fmi.springweb.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class ImportCarsDto {
    @NotEmpty
    public List<CarDto> cars;

    public ImportCarsDto() {
        cars = new ArrayList<>();
    }
}
