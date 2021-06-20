package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.impl.CompactParkingSpot;
import com.saheli.parkinglot.domain.impl.LargeParkingSpot;
import com.saheli.parkinglot.domain.impl.MotorcycleParkingSpot;
import com.saheli.parkinglot.domain.impl.ParkingLevel;
import com.saheli.parkinglot.request.ParkingSpotAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ParkingLevelMaintenanceService {

    private List<ParkingLevel> parkingLevelList;


    @Autowired
    public ParkingLevelMaintenanceService(List<ParkingLevel> parkingLevelList) {
        this.parkingLevelList = parkingLevelList;
    }

    public void addSpot(ParkingSpotAddRequest parkingSpotAddRequest) {

        Optional<ParkingLevel> parkingLevel = parkingLevelList.stream().filter(level ->
                parkingSpotAddRequest.getLevel() == level.getFloorNumber()).findAny();


        if (!parkingLevel.isPresent()) {
            log.info("Parking level does not exist!");
            throw new RuntimeException("No such level!");
        }

        ParkingLevel parkingLevelToAddSpot = parkingLevel.get();

        if (parkingSpotAddRequest.getSlotNumber() != parkingLevelToAddSpot
                .getParkingSpotsForFloor().size() + 1) {
            log.info("Cannot add a non-continuous parking slot!");
            throw new RuntimeException("Non continuous parking slot!");
        }

        ParkingSpot parkingSlot = buildParkingSlot(parkingLevelToAddSpot, parkingSpotAddRequest);
        parkingLevelToAddSpot.getParkingSpotsForFloor().add(parkingSlot);
    }

    private ParkingSpot buildParkingSlot(ParkingLevel parkingLevelToAddSpot, ParkingSpotAddRequest parkingSpotAddRequest) {

        int rowNumber = parkingSpotAddRequest.getRowNumber();

        switch (parkingSpotAddRequest.getCategory()) {
            case LARGE_SPOT:
                return LargeParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .number(parkingLevelToAddSpot.getParkingSpotsForFloor().size() + 1)
                        .build();
            case COMPACT_SPOT:
                return CompactParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .number(parkingLevelToAddSpot.getParkingSpotsForFloor().size() + 1)
                        .build();
            case MOTORCYCLE_SPOT:
                return MotorcycleParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .number(parkingLevelToAddSpot.getParkingSpotsForFloor().size() + 1)
                        .build();
        }

        return null;
    }
}
