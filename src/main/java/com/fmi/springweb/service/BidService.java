package com.fmi.springweb.service;

import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.BidRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    private final BidRepository bidRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public List<BidEntity> computeWinningBidsForUser(UserEntity user) {
        Optional<List<BidEntity>> listOfBids = bidRepository.findBidEntitiesByUsernameAndWinningBidIsTrue(user);
        return listOfBids.orElse(null);
    }
}
