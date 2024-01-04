package com.fmi.springweb.service;

import com.fmi.springweb.dto.AuctionDto;
import com.fmi.springweb.dto.StartAuctionDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.repository.AuctionRepository;
import com.fmi.springweb.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final CarRepository carRepository;

    public AuctionService(AuctionRepository auctionRepository, CarRepository carRepository) {
        this.auctionRepository = auctionRepository;
        this.carRepository = carRepository;
    }

    public BidEntity getMaxBidFromAuction(AuctionEntity auctionEntity) {
        return auctionEntity.getBids().stream()
                .max(Comparator.comparingDouble(BidEntity::getBidPrice))
                .orElse(null);
    }

    public void outbidLatestBidFromAuction(AuctionEntity auctionEntity, BidEntity bidEntity) {
        for (BidEntity bid : auctionEntity.getBids()) {
            if (Objects.equals(bid.getBidId(), bidEntity.getBidId())) {
                bid.setWinningBid(false);
                break;
            }
        }
        auctionRepository.save(auctionEntity);
    }

    public void startAuctionForCar(StartAuctionDto model) throws InvalidAuctionException {
        CarEntity carEntity = carRepository.findById(model.carId).orElse(null);

        if (carEntity == null) {
            throw new InvalidAuctionException("Invalid Car Id provided");
        }

        if (auctionRepository.findAuctionEntitiesByCar(carEntity).isPresent()) {
            throw new InvalidAuctionException("Car already auctioned");
        }

        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setStartDate(new Date());
        auctionEntity.setEndDate(new Date(new Date().getTime() + model.days * 24 * 60 * 60 * 1000));
        auctionEntity.setCar(carEntity);

        auctionRepository.save(auctionEntity);
    }

    public List<AuctionDto> getAvailableAuctions() {
        return auctionRepository
                .findAuctionEntitiesByStartDateBeforeAndEndDateAfter(new Date(), new Date())
                .orElse(new ArrayList<>())
                .stream()
                .map((auction) -> new AuctionDto(
                        auction.getAuctionId(),
                        auction.getCar().getCarModel(),
                        auction.getCar().getCarBrand().getBrandName(),
                        auction.getCar().getCarId(),
                        auction.getStartDate(),
                        auction.getEndDate()))
                .collect(Collectors.toList());
    }

}
