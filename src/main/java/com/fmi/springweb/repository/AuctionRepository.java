package com.fmi.springweb.repository;

import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Long> {
    Optional<AuctionEntity> findAuctionEntityByAuctionId(Long auctionId);
    Optional<AuctionEntity> findAuctionEntityByCar(CarEntity carEntity);
    Optional<List<AuctionEntity>> findAuctionEntitiesByBidsInAndEndDateBefore(List<BidEntity> listOfBids, Date endDate);
    Optional<List<AuctionEntity>> findAuctionEntitiesByStartDateBeforeAndEndDateAfter(Date startDate, Date endDate);
}
