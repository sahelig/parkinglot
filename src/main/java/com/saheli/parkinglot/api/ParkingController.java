package com.saheli.parkinglot.api;

import com.saheli.parkinglot.exception.ParkingSpaceNotAvailableException;
import com.saheli.parkinglot.exception.VehicleNotFoundException;
import com.saheli.parkinglot.request.ParkRequest;
import com.saheli.parkinglot.request.VacateRequest;
import com.saheli.parkinglot.service.AutoValetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("v1/parkinglot/")
public class ParkingController {

    private AutoValetService autoValetService;

    @Autowired
    public ParkingController(AutoValetService autoValetService) {
        this.autoValetService = autoValetService;
    }

    @PostMapping("/park")
    @ResponseBody
    public ResponseEntity park(@RequestBody ParkRequest parkRequest) {
        try {
            String park = autoValetService.park(parkRequest);
            return ResponseEntity.ok(park);
        } catch (ParkingSpaceNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/unpark")
    @ResponseBody
    public ResponseEntity unpark(@RequestBody VacateRequest vacateRequest) {
        try {
            String park = autoValetService.vacate(vacateRequest);
            return ResponseEntity.ok(park);
        } catch (VehicleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
