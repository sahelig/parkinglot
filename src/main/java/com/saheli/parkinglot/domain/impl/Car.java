package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.VehicleType;

public class Car extends Vehicle {

    public Car() {
        super(VehicleType.CAR);
    }
}
