package com.fmi.springweb.controller;

import com.fmi.springweb.component.RequestHandler;
import com.fmi.springweb.dto.StartAuctionDto;
import com.fmi.springweb.dto.ImportCarsDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.exceptions.InvalidAuctionException;
import com.fmi.springweb.service.AuctionService;
import com.fmi.springweb.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final CarService carService;
    private final AuctionService auctionService;
    public AdminController(CarService carService, AuctionService auctionService) {
        this.carService = carService;
        this.auctionService = auctionService;
    }

    /**
     * Import list of cars. Requires admin role
     * @param cars Array of cars
     * @return 200 OK for successful import, 502 for Server Error
     */
    @PostMapping("/admin/car/import")
    public ResponseEntity<ResponseDto<Object>> importCars(@RequestBody ImportCarsDto cars) {
        return RequestHandler.handleRequest(() -> {
            this.carService.importCarsFromList(cars);
            return ResponseEntity.ok(new ResponseDto<>(true, null));
        }, null);
    }

    /**
     * Start car auction. Requires admin role
     * @param model Settings for auction
     * @return 200 OK for successful import, 502 for Server Error
     */
    @PostMapping("/admin/auction/start")
    public ResponseEntity<ResponseDto<Object>> startAuction(@RequestBody StartAuctionDto model) {
        return RequestHandler.handleRequest(() -> {
            this.auctionService.startAuctionForCar(model);
            return ResponseEntity.ok(new ResponseDto<>(true, null));
        }, InvalidAuctionException.class);
    }

    /**
     * Start car auction. Requires admin role
     * @param id ID of auction
     * @return 200 OK for successful import, 502 for Server Error
     */
    @PostMapping("/admin/auction/stop/{id}")
    public ResponseEntity<ResponseDto<Object>> stopAuction(@PathVariable("id") Long id) {
        return RequestHandler.handleRequest(() -> {
            this.auctionService.stopAuction(id);
            return ResponseEntity.ok(new ResponseDto<>(true, null));
        }, InvalidAuctionException.class);
    }
}
