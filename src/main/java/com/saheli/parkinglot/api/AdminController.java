package com.saheli.parkinglot.api;

import com.saheli.parkinglot.request.ParkingSpotAddRequest;
import com.saheli.parkinglot.service.ParkingLevelMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("v1/parkinglot/")
public class AdminController {

    private ParkingLevelMaintenanceService parkingLevelMaintenanceService;

    @Autowired
    public AdminController(ParkingLevelMaintenanceService parkingLevelMaintenanceService) {
        this.parkingLevelMaintenanceService = parkingLevelMaintenanceService;
    }

    @PostMapping("/addspot")
    @ResponseBody
    public String addSpot(@RequestBody ParkingSpotAddRequest parkingSpotAddRequest) {
        parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest);
        return parkingSpotAddRequest.toString();
    }
}
