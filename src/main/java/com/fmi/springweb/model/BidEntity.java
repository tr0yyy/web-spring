package com.fmi.springweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name="bids")
@Getter
@Setter
public class BidEntity {
    @Id
    @GeneratedValue
    private Long bidId;
    @ManyToOne
    @JoinColumn(name="username")
    private UserEntity username;
    private Date bidDate;
    private Float bidPrice;
    private boolean winningBid;

    @Override
    public String toString() {
        return "BidEntity{" +
                "bidId=" + bidId +
                ", username=" + username +
                ", bidDate=" + bidDate +
                ", bidPrice=" + bidPrice +
                ", winningBid=" + winningBid +
                '}';
    }
}
