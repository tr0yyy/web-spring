package com.fmi.springweb.service;

import com.fmi.springweb.exceptions.InvalidBidException;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.AuctionRepository;
import com.fmi.springweb.repository.BidRepository;
import com.fmi.springweb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("UnitTests")
public class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionService auctionService;

    @Test
    public void testComputeWinningBidsForUserWithValidUser() {
        UserEntity user = new UserEntity();
        BidEntity bid1 = new BidEntity();
        BidEntity bid2 = new BidEntity();

        List<BidEntity> bidEntities = new ArrayList<>();
        bidEntities.add(bid1);
        bidEntities.add(bid2);

        when(bidRepository.findBidEntitiesByUsernameAndWinningBidIsTrue(user)).thenReturn(Optional.of(bidEntities));

        List<BidEntity> result = bidService.computeWinningBidsForUser(user);

        verify(bidRepository, times(1)).findBidEntitiesByUsernameAndWinningBidIsTrue(user);

        assertEquals(2, result.size());
    }

    @Test
    public void testComputeWinningBidsForUserWithNoBids() {
        UserEntity user = new UserEntity();

        when(bidRepository.findBidEntitiesByUsernameAndWinningBidIsTrue(user)).thenReturn(Optional.empty());

        List<BidEntity> result = bidService.computeWinningBidsForUser(user);

        verify(bidRepository, times(1)).findBidEntitiesByUsernameAndWinningBidIsTrue(user);

        assertNull(result);
    }

    @Test
    public void testPlaceBidWithValidParameters() throws InvalidBidException {
        UserEntity user = new UserEntity();
        user.setUsername("TestUser");
        user.setFunds(1000.0f);

        AuctionEntity auction = new AuctionEntity();
        auction.setAuctionId(1L);
        auction.setStartDate(new Date(System.currentTimeMillis() - 1000));
        auction.setEndDate(new Date(System.currentTimeMillis() + 1000));

        when(userRepository.findByUsername("TestUser")).thenReturn(Optional.of(user));
        when(auctionRepository.findAuctionEntityByAuctionId(1L)).thenReturn(Optional.of(auction));
        when(auctionService.getMaxBidFromAuction(auction)).thenReturn(null);

        bidService.placeBid("TestUser", 1L, 500.0f);

        verify(userRepository, times(1)).findByUsername("TestUser");
        verify(auctionRepository, times(1)).findAuctionEntityByAuctionId(1L);
        assertEquals(500.0f, user.getFunds());
        verify(bidRepository, times(1)).save(any(BidEntity.class));
    }
}
