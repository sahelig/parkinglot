package com.saheli.parkinglot.domain;

import com.saheli.parkinglot.domain.impl.SlotOptions;
import com.saheli.parkinglot.enums.VehicleType;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Vehicle {

    private String licenceNumber;
    private VehicleType vehicleType;
    private List<ParkingSpot> currentlyParkedAt;

    public Vehicle(VehicleType type) {
        vehicleType = type;
    }

    public List<SlotOptions> getSlotOptions() {
        return Collections.emptyList();
    }

    public void addToParkingSpot(ParkingSpot parkingSpot) {
        currentlyParkedAt.add(parkingSpot);
    }
}
