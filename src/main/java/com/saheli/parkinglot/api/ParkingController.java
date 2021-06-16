package com.saheli.parkinglot.api;

import com.saheli.parkinglot.request.CarParkRequest;
import com.saheli.parkinglot.service.AutoValetService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String parkACar(@RequestBody CarParkRequest carParkRequest) {
        return carParkRequest.toString();
    }
}
