package com.fmi.springweb.dto;

import java.util.Date;

public class BidDto {
    public Long bidId;
    public String username;
    public Date bidDate;
    public Float bidPrice;
    public boolean winningBid;

    public BidDto(Long bidId, String username, Date bidDate, Float bidPrice, boolean winningBid) {
        this.bidId = bidId;
        this.username = username;
        this.bidDate = bidDate;
        this.bidPrice = bidPrice;
        this.winningBid = winningBid;
    }
}
