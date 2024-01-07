package com.fmi.springweb.service;

import com.fmi.springweb.dto.CarDto;
import com.fmi.springweb.dto.ImportCarsDto;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.CarBrandEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.model.UserEntity;
import com.fmi.springweb.repository.CarBrandRepository;
import com.fmi.springweb.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("UnitTests")
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarBrandRepository carBrandRepository;

    @Mock
    private BidService bidService;

    @Test
    public void testComputeCarsForUserWithValidUser() {
        // Mock data
        UserEntity user = new UserEntity();
        AuctionEntity auction1 = new AuctionEntity();
        AuctionEntity auction2 = new AuctionEntity();
        auction1.setCar(new CarEntity());
        auction2.setCar(new CarEntity());

        List<AuctionEntity> auctionEntities = new ArrayList<>();
        auctionEntities.add(auction1);
        auctionEntities.add(auction2);

        when(bidService.getAuctionsForUser(user)).thenReturn(auctionEntities);

        List<CarEntity> result = carService.computeCarsForUser(user);

        verify(bidService, times(1)).getAuctionsForUser(user);
        assertEquals(2, result.size());
    }

    @Test
    public void testComputeCarsForUserWithNoAuctions() {
        UserEntity user = new UserEntity();

        when(bidService.getAuctionsForUser(user)).thenReturn(new ArrayList<>());

        List<CarEntity> result = carService.computeCarsForUser(user);

        assertNull(result);
    }

    @Test
    public void testImportCarsFromList() {
        ImportCarsDto importCarsDto = new ImportCarsDto();
        importCarsDto.cars.add(new CarDto("Brand", "Model", "Test", 2022, 1000, 50000.0F));
        CarBrandEntity carBrandEntity = new CarBrandEntity();

        when(carBrandRepository.insertIfNotExists("Brand", "Country")).thenReturn(carBrandEntity);

        carService.importCarsFromList(importCarsDto);

        verify(carRepository, times(1)).save(any(CarEntity.class));
    }
}