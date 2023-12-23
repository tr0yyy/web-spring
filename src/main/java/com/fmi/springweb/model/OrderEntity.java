package com.fmi.springweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name="orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long orderId;
    private long fundsAdded;
}
