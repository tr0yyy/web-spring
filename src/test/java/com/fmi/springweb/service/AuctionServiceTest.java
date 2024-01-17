package com.fmi.springweb.service;

import com.fmi.springweb.dto.AuctionDto;
import com.fmi.springweb.dto.StartAuctionDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.model.AuctionEntity;
import com.fmi.springweb.model.CarBrandEntity;
import com.fmi.springweb.model.CarEntity;
import com.fmi.springweb.repository.AuctionRepository;
import com.fmi.springweb.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("UnitTests")
public class AuctionServiceTest {

    @InjectMocks
    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private CarRepository carRepository;

    @Test
    public void testStartAuctionForCarWithValidCarId() throws InvalidAuctionException {
        StartAuctionDto startAuctionDto = new StartAuctionDto();
        startAuctionDto.carId = 1L;

        CarEntity carEntity = new CarEntity();
        carEntity.setCarId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(carEntity));
        when(auctionRepository.findAuctionEntityByCar(carEntity)).thenReturn(Optional.empty());

        auctionService.startAuctionForCar(startAuctionDto);

        verify(auctionRepository, times(1)).save(any(AuctionEntity.class));
    }

    @Test
    public void testStartAuctionForCarWithInvalidCarId() {
        StartAuctionDto startAuctionDto = new StartAuctionDto();
        startAuctionDto.carId = 1L;

        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidAuctionException.class, () -> auctionService.startAuctionForCar(startAuctionDto));

        verify(auctionRepository, never()).save(any(AuctionEntity.class));
    }

    @Test
    public void testGetAvailableAuctionsWithValidAuctions() {
        CarBrandEntity carBrandEntity = new CarBrandEntity(1L, "Test", "Test");
        CarEntity carEntity = new CarEntity(carBrandEntity, "Test", 1, 1, 1);
        AuctionEntity auction1 = new AuctionEntity();
        auction1.setAuctionId(1L);
        auction1.setCar(carEntity);
        AuctionEntity auction2 = new AuctionEntity();
        auction2.setAuctionId(2L);
        auction2.setCar(carEntity);

        List<AuctionEntity> auctionEntities = Arrays.asList(auction1, auction2);

        when(auctionRepository.findAuctionEntitiesByStartDateBeforeAndEndDateAfter(any(Date.class), any(Date.class)))
                .thenReturn(Optional.of(auctionEntities));

        List<AuctionDto> result = auctionService.getAvailableAuctions();


        assertEquals(2, result.size());
    }

    @Test
    public void testGetAvailableAuctionsWithNoAuctions() {
        when(auctionRepository.findAuctionEntitiesByStartDateBeforeAndEndDateAfter(any(Date.class), any(Date.class)))
                .thenReturn(Optional.empty());

        List<AuctionDto> result = auctionService.getAvailableAuctions();

        verify(auctionRepository, times(1))
                .findAuctionEntitiesByStartDateBeforeAndEndDateAfter(any(Date.class), any(Date.class));

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
