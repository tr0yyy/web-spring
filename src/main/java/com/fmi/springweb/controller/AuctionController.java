package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/core/auction/list")
    public ResponseEntity<ResponseDto> listAvailableAuctions() {
        return RequestHandler.handleRequest(() -> ResponseEntity.ok(new ResponseDto(true, this.auctionService.getAvailableAuctions())), null);
    }

    @GetMapping("/core/auction/describe/auction={id}")
    public ResponseEntity<ResponseDto> describeAuction(@PathVariable("id") Long auctionId) {
        return RequestHandler.handleRequest(() ->
                        ResponseEntity.ok(
                                new ResponseDto(true, this.auctionService.computeDetailsFromAuction(auctionId))
                        ),
                InvalidAuctionException.class);
    }
}