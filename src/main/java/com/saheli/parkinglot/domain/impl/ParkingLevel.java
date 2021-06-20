package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@NoArgsConstructor
@ToString
public class ParkingLevel {

    private int floorNumber;
    private volatile List<ParkingSpot> parkingSpotsForFloor = new ArrayList<>();
    private volatile Map<String, Vehicle> vehiclesOnFloorCurrently = new HashMap<>();

    ParkingLevel(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public synchronized int parkVehicle(Vehicle vehicle) {

        boolean atleastOneAvailableSlot = parkingSpotsForFloor.stream().anyMatch(spot -> !spot.isOccupied());

        if (!atleastOneAvailableSlot) {
            return -1;
        }

        List<SlotOption> slotOptions = vehicle.getSlotOptions();
        int slotStart = -1;
        SlotOption slot = null;

        for (SlotOption slotOption : slotOptions) {
            slotStart = startOfContiguousSlotsAvailableOnLevel(slotOption.numOfSlots, slotOption.category);
            if (slotStart != -1) {
                slot = slotOption;
                log.info("Slot obtained from :" + slotStart);
                break;
            }
        }

        if (slotStart != -1) {
            log.info("Slot found, try and assign vehicle");
            assignVehicleToSpot(vehicle, slotStart, slot.numOfSlots);
        }

        return slotStart;
    }

    public synchronized boolean locateAndRemoveVehicle(String licenseNumberOfVehicle) {

        if (!vehiclesOnFloorCurrently.containsKey(licenseNumberOfVehicle)) {
            return false;
        }

        removeVehicleFromSpot(vehiclesOnFloorCurrently.get(licenseNumberOfVehicle));
        return true;
    }

    private synchronized void assignVehicleToSpot(Vehicle vehicle, int startSlotNumber, int
            numOfSlotsReq) {
        for (int i = 0; i < numOfSlotsReq; i++) {
            int index = i + startSlotNumber - 1;
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


    private int startOfContiguousSlotsAvailableOnLevel(int requiredSlots, ParkingSpotCategory parkingSpotCategory) {

        if (parkingSpotsForFloor.isEmpty()) {
            return -1;
        }

        int conseq = 0;
        int prev = parkingSpotsForFloor.get(0).getNumber();
        int prevRow = parkingSpotsForFloor.get(0).getNumber();
        int startOfThisSlot = -1;

        for (ParkingSpot parkingSpot : parkingSpotsForFloor) {

            ParkingSpotCategory category = parkingSpot.getCategory();
            if (parkingSpot.getNumber() == prev + 1
                    && parkingSpot.getRowNumber() == prevRow
                    && !parkingSpot.isOccupied()
                    && parkingSpotCategory.equals(category)) {
                conseq++;
            } else if (!parkingSpot.isOccupied() && parkingSpotCategory.equals(category)) {
                conseq = 1;
            }

            if (conseq == 1) {
                startOfThisSlot = parkingSpot.getNumber();
            }

            prev = parkingSpot.getNumber();
            prevRow = parkingSpot.getRowNumber();

            if (conseq == requiredSlots) {
                log.info(requiredSlots + " consecutive slots found, starting from " + parkingSpot.getNumber());
                return startOfThisSlot;
            }

        }

        return -1;
    }


}
