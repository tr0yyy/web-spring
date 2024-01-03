package com.fmi.springweb.service;

import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.AuctionRepository;
import com.fmi.springweb.repository.BidRepository;
import com.fmi.springweb.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    public BidService(BidRepository bidRepository, AuctionRepository auctionRepository, UserRepository userRepository, AuctionService auctionService) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.auctionService = auctionService;
    }

    public List<BidEntity> computeWinningBidsForUser(UserEntity user) {
        Optional<List<BidEntity>> listOfBids = bidRepository.findBidEntitiesByUsernameAndWinningBidIsTrue(user);
        return listOfBids.orElse(null);
    }

    public List<AuctionEntity> getAuctionsForUser(UserEntity user) {
        List<BidEntity> bidEntities = computeWinningBidsForUser(user);
        if (bidEntities == null || bidEntities.isEmpty()) {
            return null;
        }

        return auctionRepository.findAuctionEntitiesByBidsIn(bidEntities).orElse(null);
    }


    public void placeBid(String username, Long auctionId, Float funds) throws InvalidBidException {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);

        if (userEntity == null) {
            throw new InvalidBidException("Missing user from platform");
        }

        AuctionEntity auctionEntity = auctionRepository.findAuctionEntityByAuctionId(auctionId).orElse(null);

        if (auctionEntity == null) {
            throw new InvalidBidException("Auction not found");
        }

        if (new Date().getTime() < auctionEntity.getStartDate().getTime() || new Date().getTime() > auctionEntity.getEndDate().getTime()) {
            throw new InvalidBidException("You cannot bid outside of auction's window");
        }

        if (funds >= userEntity.getFunds()) {
            throw new InvalidBidException("Insufficient funds for bid");
        }

        BidEntity currentMaxBid = auctionService.getMaxBidFromAuction(auctionEntity);

        if (currentMaxBid != null) {
            auctionService.outbidLatestBidFromAuction(auctionEntity, currentMaxBid);
            UserEntity currentMaxUser = currentMaxBid.getUsername();
            currentMaxUser.setFunds(currentMaxBid.getUsername().getFunds() + currentMaxBid.getBidPrice());
            userRepository.save(currentMaxUser);
        }

        userEntity.setFunds(userEntity.getFunds() - funds);
        userRepository.save(userEntity);

        BidEntity newMaxBid = new BidEntity();
        newMaxBid.setUsername(userEntity);
        newMaxBid.setBidPrice(funds);
        newMaxBid.setBidDate(new Date());
        newMaxBid.setWinningBid(true);

        List<BidEntity> currentBids = auctionEntity.getBids();
        currentBids.add(newMaxBid);
        auctionEntity.setBids(currentBids);
        auctionRepository.save(auctionEntity);
    }
}
