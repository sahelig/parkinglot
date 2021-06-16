package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;

public class LargeParkingSpot implements ParkingSpot {

    int number;
    boolean isOccupied;
    String licenseOfVehicleParked;

    public LargeParkingSpot(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public ParkingSpotCategory getCategory() {
        return ParkingSpotCategory.LARGE_SPOT;
    }

    @Override
    public void assignVehicle(Vehicle vehicle) {
        licenseOfVehicleParked = vehicle.getLicenceNumber();
        isOccupied = true;
    }

    @Override
    public void removeVehicle() {
        licenseOfVehicleParked = null;
        isOccupied = false;
    }
}
