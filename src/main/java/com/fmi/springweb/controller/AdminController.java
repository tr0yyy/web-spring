package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.StartAuctionDto;
import com.fmi.springweb.dto.ImportCarsDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.service.AuctionService;
import com.fmi.springweb.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final CarService carService;
    private final AuctionService auctionService;
    public AdminController(CarService carService, AuctionService auctionService) {
        this.carService = carService;
        this.auctionService = auctionService;
    }

    @PostMapping("/admin/car/import")
    public ResponseEntity<ResponseDto> importCars(@RequestBody ImportCarsDto cars) {
        return RequestHandler.handleRequest(() -> {
            this.carService.importCarsFromList(cars);
            return ResponseEntity.ok(new ResponseDto(true, null));
        }, null);
    }

    @PostMapping("/admin/auction/start")
    public ResponseEntity<ResponseDto> startAuction(@RequestBody StartAuctionDto model) {
        return RequestHandler.handleRequest(() -> {
            this.auctionService.startAuctionForCar(model);
            return ResponseEntity.ok(new ResponseDto(true, null));
        }, InvalidAuctionException.class);
    }
}
