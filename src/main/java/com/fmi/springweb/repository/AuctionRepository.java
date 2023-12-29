package com.fmi.springweb.repository;

import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
    Optional<List<AuctionEntity>> findAuctionEntitiesByBidsIn(List<BidEntity> listOfBids);
}
