package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LargeParkingSpot implements ParkingSpot {

    private final int number;
    private final int rowNumber;
    private volatile boolean isOccupied;
    private volatile String licenseOfVehicleParked;

    public LargeParkingSpot(int number, int rowNumber) {
        this.number = number;
        this.rowNumber = rowNumber;
    }

    @Override
    public ParkingSpotCategory getCategory() {
        return ParkingSpotCategory.LARGE_SPOT;
    }

    @Override
    public synchronized void assignVehicle(Vehicle vehicle) {
        licenseOfVehicleParked = vehicle.getLicenceNumber();
        isOccupied = true;
    }

    @Override
    public synchronized void removeVehicle() {
        licenseOfVehicleParked = null;
        isOccupied = false;
    }
}
