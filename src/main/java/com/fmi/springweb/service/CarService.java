package com.fmi.springweb.service;

import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final AuctionService auctionService;

    public CarService(CarRepository carRepository, AuctionService auctionService) {
        this.carRepository = carRepository;
        this.auctionService = auctionService;
    }

    public List<CarEntity> computeCarsForUser(UserEntity user) {
        List<AuctionEntity> auctionEntities = auctionService.getAuctionsForUser(user);
        if (auctionEntities == null || auctionEntities.isEmpty()) {
            return null;
        }

        return auctionEntities.stream()
                .map(AuctionEntity::getCar)
                .collect(Collectors.toList());
    }
}
