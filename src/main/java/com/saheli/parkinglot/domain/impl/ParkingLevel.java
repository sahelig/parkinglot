package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
public class ParkingLevel {

    private int floorNumber;
    private List<ParkingSpot> parkingSpotsForFloor;

    ParkingLevel(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int isSlotAvailable(Vehicle vehicle) {

        boolean atleastOneAvailableSlot = parkingSpotsForFloor.stream().anyMatch(spot -> !spot.isOccupied());

        if (!atleastOneAvailableSlot) {
            return -1;
        }

        List<SlotOptions> slotOptions = vehicle.getSlotOptions();
        int slotStart = -1;

        for (SlotOptions slotOption : slotOptions) {
            slotStart = startOfContiguousSlotsAvailableOnLevel(slotOption.numOfSlots, slotOption.category);
            if (slotStart != -1) {
                log.info("Slot obtained from :" + slotStart);
                break;
            }
        }

        return slotStart;
    }

    public void assignVehicleToSpot(Vehicle vehicle, int startSlotNumber, int numOfSlotsReq) {
        for (int i = startSlotNumber; i <= (startSlotNumber + numOfSlotsReq); i++) {
            log.info("Assigning vehicle " + vehicle + " to slot " + parkingSpotsForFloor.get(i));
            parkingSpotsForFloor.get(i).assignVehicle(vehicle);
            vehicle.addToParkingSpot(parkingSpotsForFloor.get(i));
        }
    }

    public void removeVehicleFromSpot(Vehicle vehicle) {
        List<ParkingSpot> currentlyParkedAt = vehicle.getCurrentlyParkedAt();

        for (ParkingSpot parkingSpot : currentlyParkedAt) {
            parkingSpot.removeVehicle();
        }

        vehicle.getCurrentlyParkedAt().removeAll(currentlyParkedAt);
    }


    private int startOfContiguousSlotsAvailableOnLevel(int requiredSlots, ParkingSpotCategory parkingSpotCategory) {

        if (parkingSpotsForFloor.isEmpty()) {
            return -1;
        }

        int conseq = 1;
        int prev = parkingSpotsForFloor.get(0).getNumber();

        for (ParkingSpot parkingSpot : parkingSpotsForFloor) {

            if (parkingSpot.getNumber() == prev + 1 && !parkingSpot.isOccupied() && parkingSpotCategory.equals(parkingSpot.getCategory())) {
                conseq++;
            } else {
                conseq = 1;
            }

            prev = parkingSpot.getNumber();

            if (conseq == requiredSlots) {
                log.info(requiredSlots + " consecutive slots found, starting from " + parkingSpot.getNumber());
                return parkingSpot.getNumber();
            }

        }

        return -1;
    }


}
