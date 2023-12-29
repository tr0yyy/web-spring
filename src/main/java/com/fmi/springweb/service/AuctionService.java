package com.fmi.springweb.service;

import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final BidService bidService;

    public AuctionService(AuctionRepository auctionRepository, BidService bidService) {
        this.auctionRepository = auctionRepository;
        this.bidService = bidService;
    }

    public List<AuctionEntity> getAuctionsForUser(UserEntity user) {
        List<BidEntity> bidEntities = bidService.computeWinningBidsForUser(user);
        if (bidEntities == null || bidEntities.isEmpty()) {
            return null;
        }

        return auctionRepository.findAuctionEntitiesByBidsIn(bidEntities).orElse(null);
    }
}
