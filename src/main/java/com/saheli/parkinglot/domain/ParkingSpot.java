package com.saheli.parkinglot.domain;

import com.saheli.parkinglot.enums.ParkingSpotCategory;

public interface ParkingSpot {

    int getNumber();

    int getRowNumber();

    boolean isOccupied();

    ParkingSpotCategory getCategory();

    void assignVehicle(Vehicle vehicle);

    void removeVehicle();
}
