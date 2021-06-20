package com.saheli.parkinglot.domain;

import com.saheli.parkinglot.domain.impl.SlotOption;
import com.saheli.parkinglot.enums.VehicleType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vehicle {

    private String licenceNumber;
    private final VehicleType vehicleType;
    private final List<SlotOption> slotOptions;
    private volatile int currentLevel = -1;
    private volatile List<ParkingSpot> currentlyParkedAt = new ArrayList<>();

    public Vehicle(VehicleType type, List<SlotOption> slotOptions) {
        vehicleType = type;
        this.slotOptions = slotOptions;
    }

    public synchronized void addToParkingSpot(ParkingSpot parkingSpot) {
        currentlyParkedAt.add(parkingSpot);
    }

    public synchronized void addToLevel(int floorNumber) {
        currentLevel = floorNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!licenceNumber.equals(vehicle.licenceNumber)) return false;
        return vehicleType == vehicle.vehicleType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + licenceNumber.hashCode();
        result = 31 * result + vehicleType.hashCode();
        return result;
    }
}
