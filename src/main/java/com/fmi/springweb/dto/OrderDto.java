package com.fmi.springweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderDto {
    @NotNull
    public String username;
    @Min(0)
    public String funds;
}
