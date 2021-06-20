package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import com.saheli.parkinglot.enums.VehicleType;

import java.util.Arrays;

public class Bus extends Vehicle {

    public Bus() {
        super(VehicleType.BUS, Arrays.asList(new SlotOption(5, ParkingSpotCategory.LARGE_SPOT)));
    }

}
