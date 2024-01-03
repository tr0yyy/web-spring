package com.fmi.springweb.service;

import com.fmi.springweb.dto.CarDto;
import com.fmi.springweb.dto.ImportCarsDto;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.CarBrandEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.CarBrandRepository;
import com.fmi.springweb.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarBrandRepository carBrandRepository;
    private final BidService bidService;

    public CarService(CarRepository carRepository,
                      CarBrandRepository carBrandRepository,
                      BidService bidService) {
        this.carRepository = carRepository;
        this.bidService = bidService;
        this.carBrandRepository = carBrandRepository;
    }

    public List<CarEntity> computeCarsForUser(UserEntity user) {
        List<AuctionEntity> auctionEntities = bidService.getAuctionsForUser(user);
        if (auctionEntities == null || auctionEntities.isEmpty()) {
            return null;
        }

        return auctionEntities.stream()
                .map(AuctionEntity::getCar)
                .collect(Collectors.toList());
    }

    public void importCarsFromList(ImportCarsDto cars) {
        for (CarDto carDto : cars.cars) {
            CarBrandEntity carBrandEntity = carBrandRepository.insertIfNotExists(carDto.carBrand, carDto.country);
            CarEntity carEntity = new CarEntity(carBrandEntity, carDto.carModel, carDto.manufactureYear, carDto.kilometers, carDto.initialPrice);
            carRepository.save(carEntity);
        }
    }
}
