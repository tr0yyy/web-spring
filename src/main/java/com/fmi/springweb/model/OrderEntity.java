package com.fmi.springweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderEntity {

    @Id
    private long orderId;
    @ManyToOne
    @JoinColumn(name = "username")
    private UserEntity username;

    private long fundsAdded;
}
