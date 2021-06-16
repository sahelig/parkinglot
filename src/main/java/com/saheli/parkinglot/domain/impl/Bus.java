package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import com.saheli.parkinglot.enums.VehicleType;

import java.util.Arrays;
import java.util.List;

public class Bus extends Vehicle {

    public Bus() {
        super(VehicleType.BUS);
    }

    public List<SlotOptions> getSlotOptions() {
        return Arrays.asList(new SlotOptions(5, ParkingSpotCategory.LARGE_SPOT));
    }
}
