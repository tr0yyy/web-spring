package com.fmi.springweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name="auctions")
@Getter
@Setter
public class AuctionEntity {
    @Id
    @GeneratedValue
    private Long auctionId;
    @OneToMany
    private List<BidEntity> bids;
    @ManyToOne
    @JoinColumn(name="carId")
    private CarEntity car;
    private Date startDate;
    private Date endDate;
}
