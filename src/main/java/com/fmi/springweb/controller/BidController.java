package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.BidDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }
    @PostMapping("/core/bid/place")
    public ResponseEntity<ResponseDto> placeBid(@RequestBody BidDto bidDto) {
        return RequestHandler.handleRequest(() -> {
            this.bidService.placeBid(bidDto.username, bidDto.auctionId, bidDto.funds);
            return ResponseEntity.ok(new ResponseDto(true, "Bid placed successfully"));
        }, InvalidBidException.class);
    }
}
