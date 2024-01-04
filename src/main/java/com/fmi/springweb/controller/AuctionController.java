package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.AuctionDto;
import com.fmi.springweb.dto.BidDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    /**
     * List Available Auctions. Requires User Role
     * @return 200 OK with array of auctions, 502 if Server Error
     */
    @GetMapping("/core/auction/list")
    public ResponseEntity<ResponseDto<List<AuctionDto>>> listAvailableAuctions() {
        return RequestHandler.handleRequest(() -> ResponseEntity.ok(new ResponseDto<>(true, this.auctionService.getAvailableAuctions())), null);
    }

    /**
     * Describe an auction (display maximum bids of users). Requires User Role
     * @param auctionId auctionId from database
     * @return 200 OK with auction details, 200 OK with error message if auction does not exist, 502 if Server Error
     */
    @GetMapping("/core/auction/describe/auction={id}")
    public ResponseEntity<ResponseDto<List<BidDto>>> describeAuction(@PathVariable("id") Long auctionId) {
        return RequestHandler.handleRequest(() ->
                        ResponseEntity.ok(
                                new ResponseDto<>(true, this.auctionService.computeDetailsFromAuction(auctionId))
                        ),
                InvalidAuctionException.class);
    }
}
