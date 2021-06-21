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
public class CompactParkingSpot implements ParkingSpot {

    private final int rowNumber;
    private final int column;
    private volatile boolean isOccupied;
    private volatile String licenseOfVehicleParked;

    public CompactParkingSpot(int column, int rowNumber) {
        this.column = column;
        this.rowNumber = rowNumber;
    }

    @Override
    public ParkingSpotCategory getCategory() {
        return ParkingSpotCategory.COMPACT_SPOT;
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
