package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.impl.ParkingLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoValetService {

    private List<ParkingLevel> parkingLevelList;

    @Autowired
    public AutoValetService(List<ParkingLevel> parkingLevelList) {
        this.parkingLevelList = parkingLevelList;
    }
}
