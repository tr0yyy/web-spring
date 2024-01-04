package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.PlaceBidDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }
    @PostMapping("/core/bid/place")
    public ResponseEntity<ResponseDto> placeBid(@RequestBody PlaceBidDto placeBidDto) {
        return RequestHandler.handleRequest(() -> {
            this.bidService.placeBid(placeBidDto.username, placeBidDto.auctionId, placeBidDto.funds);
            return ResponseEntity.ok(new ResponseDto(true, "Bid placed successfully"));
        }, InvalidBidException.class);
    }
}
