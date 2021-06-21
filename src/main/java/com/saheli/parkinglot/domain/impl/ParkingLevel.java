package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Spot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Data
@Slf4j
@NoArgsConstructor
public class ParkingLevel {

    private int floorNumber;
    private volatile List<ParkingSpot> parkingSpotsForFloor = new ArrayList<>();
    @ToString.Exclude
    private volatile Map<String, Vehicle> vehiclesOnFloorCurrently = new HashMap<>();
    @ToString.Exclude
    private volatile Set<Spot> positionsPresent = new HashSet<>();

    public ParkingLevel(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public synchronized ParkingSpot parkVehicle(Vehicle vehicle) {

        if (vehiclesOnFloorCurrently.containsKey(vehicle.getLicenceNumber())) {
            log.info("Vehicle already parked, duplicate request receievd");
            return vehiclesOnFloorCurrently.get(vehicle.getLicenceNumber())
                    .getCurrentlyParkedAt().get(0);
        }

        boolean atleastOneAvailableSlot = parkingSpotsForFloor.stream().anyMatch(spot -> !spot.isOccupied());

        if (!atleastOneAvailableSlot) {
            return null;
        }

        List<SlotOption> slotOptions = vehicle.getSlotOptions();
        ParkingSpot parkingSpot;

        for (SlotOption slotOption : slotOptions) {
            parkingSpot = startOfContiguousSlotsAvailableOnLevel(slotOption.numOfSlots, slotOption.category);
            if (parkingSpot != null) {
                log.info("Slot obtained from :" + parkingSpot);
                assignVehicleToSpot(vehicle, parkingSpot, slotOption.numOfSlots);
                return parkingSpot;
            }
        }

        return null;
    }

    public synchronized boolean locateAndRemoveVehicle(String licenseNumberOfVehicle) {

        if (!vehiclesOnFloorCurrently.containsKey(licenseNumberOfVehicle)) {
            return false;
        }

        removeVehicleFromSpot(vehiclesOnFloorCurrently.get(licenseNumberOfVehicle));
        return true;
    }

    private synchronized void assignVehicleToSpot(Vehicle vehicle, ParkingSpot startSlot, int numOfSlotsReq) {

        int startSlotIndex = parkingSpotsForFloor.indexOf(startSlot);

        for (int i = 0; i < numOfSlotsReq; i++) {
            int index = i + startSlotIndex;
            log.info("Assigning vehicle " + vehicle + " to slot " + parkingSpotsForFloor.get(index));

            ParkingSpot parkingSpot = parkingSpotsForFloor.get(index);
            parkingSpot.assignVehicle(vehicle);

            vehicle.addToParkingSpot(parkingSpot);
            vehicle.addToLevel(floorNumber);
        }

        vehiclesOnFloorCurrently.put(vehicle.getLicenceNumber(), vehicle);
    }

    public synchronized void removeVehicleFromSpot(Vehicle vehicle) {
        List<ParkingSpot> currentlyParkedAt = vehicle.getCurrentlyParkedAt();

        for (ParkingSpot parkingSpot : currentlyParkedAt) {
            parkingSpot.removeVehicle();
        }

        vehicle.getCurrentlyParkedAt().removeAll(currentlyParkedAt);
        vehicle.setCurrentLevel(-1);

        vehiclesOnFloorCurrently.remove(vehicle.getLicenceNumber());
    }


    public ParkingSpot startOfContiguousSlotsAvailableOnLevel(int requiredSlots,
                                                              ParkingSpotCategory parkingSpotCategory) {

        if (parkingSpotsForFloor.isEmpty()) {
            return null;
        }

        int conseq = 0;
        int prev = -1;
        int prevRow = -1;
        ParkingSpot startOfThisSlot = null;

        for (ParkingSpot parkingSpot : parkingSpotsForFloor) {

            ParkingSpotCategory category = parkingSpot.getCategory();
            if (parkingSpot.getColumn() == prev + 1
                    && parkingSpot.getRowNumber() == prevRow
                    && !parkingSpot.isOccupied()
                    && parkingSpotCategory.equals(category)) {
                conseq++;
                prevRow = parkingSpot.getRowNumber();
                prev = parkingSpot.getColumn();
            } else if (!parkingSpot.isOccupied() && parkingSpotCategory.equals(category)) {
                conseq = 1;
                prevRow = parkingSpot.getRowNumber();
                prev = parkingSpot.getColumn();
                startOfThisSlot = parkingSpot;
            } else {
                conseq = 0;
                startOfThisSlot = null;
                prevRow = -1;
                prev = -1;
            }

            if (conseq == requiredSlots) {
                log.info(requiredSlots + " consecutive slots found, starting from " + parkingSpot.getColumn());
                return startOfThisSlot;
            }

        }

        return null;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder().append("Updated Display Board for parking level: " + floorNumber +
                "\n");
        for (ParkingSpot spot : parkingSpotsForFloor) {
            str.append("\n");
            str.append(spot);
        }

        return str.toString();
    }


}
