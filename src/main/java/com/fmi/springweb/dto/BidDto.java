package com.fmi.springweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BidDto {
    @NotNull
    public Long bidId;
    @NotNull
    public String username;
    @NotNull
    public Date bidDate;
    @NotNull
    @Min(0)
    public Float bidPrice;
    @NotNull
    public boolean winningBid;

    public BidDto(Long bidId, String username, Date bidDate, Float bidPrice, boolean winningBid) {
        this.bidId = bidId;
        this.username = username;
        this.bidDate = bidDate;
        this.bidPrice = bidPrice;
        this.winningBid = winningBid;
    }
}
