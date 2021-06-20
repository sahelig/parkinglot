package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import com.saheli.parkinglot.enums.VehicleType;

import java.util.Arrays;

public class Car extends Vehicle {

    public Car() {
        super(VehicleType.CAR, Arrays.asList(
                new SlotOption(1, ParkingSpotCategory.COMPACT_SPOT),
                new SlotOption(1, ParkingSpotCategory.LARGE_SPOT)
        ));
    }

}
