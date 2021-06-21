package com.saheli.parkinglot.domain;

import com.saheli.parkinglot.enums.ParkingSpotCategory;

public interface ParkingSpot {

    int getColumn();

    int getRowNumber();

    String getLicenseOfVehicleParked();

    boolean isOccupied();

    ParkingSpotCategory getCategory();

    void assignVehicle(Vehicle vehicle);

    void removeVehicle();
}
