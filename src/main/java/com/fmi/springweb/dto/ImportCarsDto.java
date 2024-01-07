package com.fmi.springweb.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportCarsDto {
    public List<CarDto> cars;

    public ImportCarsDto() {
        cars = new ArrayList<>();
    }
}
