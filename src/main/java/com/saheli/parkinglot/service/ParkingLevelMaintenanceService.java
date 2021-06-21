package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Spot;
import com.saheli.parkinglot.domain.impl.CompactParkingSpot;
import com.saheli.parkinglot.domain.impl.LargeParkingSpot;
import com.saheli.parkinglot.domain.impl.MotorcycleParkingSpot;
import com.saheli.parkinglot.domain.impl.ParkingLevel;
import com.saheli.parkinglot.exception.InvalidParkingSlotRequestException;
import com.saheli.parkinglot.exception.ParkingLevelNotAvailableException;
import com.saheli.parkinglot.request.ParkingSpotAddRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@Data
public class ParkingLevelMaintenanceService {

    private volatile List<ParkingLevel> parkingLevelList = new ArrayList<>();


    @Autowired
    public ParkingLevelMaintenanceService(List<ParkingLevel> parkingLevelList) {
        this.parkingLevelList = parkingLevelList;
    }

    public synchronized void addSpot(ParkingSpotAddRequest parkingSpotAddRequest) throws
            InvalidParkingSlotRequestException, ParkingLevelNotAvailableException {

        Optional<ParkingLevel> parkingLevel = parkingLevelList.stream().filter(level ->
                parkingSpotAddRequest.getLevel() == level.getFloorNumber()).findAny();


        if (!parkingLevel.isPresent()) {
            log.info("Parking level does not exist!");
            throw new ParkingLevelNotAvailableException("No such level!");
        }

        ParkingLevel parkingLevelToAddSpot = parkingLevel.get();


        Spot spot = new Spot(parkingSpotAddRequest.getRowNumber(), parkingSpotAddRequest.getColumnNumber());
        boolean slotExists = parkingLevelToAddSpot.getPositionsPresent().contains(spot);

        if (slotExists) {
            log.info("Duplicate parking slot request for this level!");
            throw new InvalidParkingSlotRequestException("Duplicate parking slot " +
                    "request");
        }

        ParkingSpot parkingSlot = buildParkingSlot(parkingSpotAddRequest);
        parkingLevelToAddSpot.getParkingSpotsForFloor().add(parkingSlot);
        parkingLevelToAddSpot.getPositionsPresent().add(spot);
        //Sort the list of parking slots added so that later the continuous check is easy
        order(parkingLevelToAddSpot.getParkingSpotsForFloor());

    }

    private static void order(List<ParkingSpot> parkingSpots) {

        Collections.sort(parkingSpots, (Comparator) (o1, o2) -> {

            int x1 = ((ParkingSpot) o1).getRowNumber();
            int x2 = ((ParkingSpot) o2).getRowNumber();


            if (x1 != x2) {
                return x1 - x2;
            }

            x1 = ((ParkingSpot) o1).getColumn();
            x2 = ((ParkingSpot) o2).getColumn();

            return x1 - x2;
        });
    }

    private ParkingSpot buildParkingSlot(ParkingSpotAddRequest parkingSpotAddRequest) {

        int rowNumber = parkingSpotAddRequest.getRowNumber();
        int columnNumber = parkingSpotAddRequest.getColumnNumber();

        switch (parkingSpotAddRequest.getCategory()) {
            case LARGE_SPOT:
                return LargeParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .column(columnNumber)
                        .build();
            case COMPACT_SPOT:
                return CompactParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .column(columnNumber)
                        .build();
            case MOTORCYCLE_SPOT:
                return MotorcycleParkingSpot.builder()
                        .isOccupied(false)
                        .licenseOfVehicleParked(null)
                        .rowNumber(rowNumber)
                        .column(columnNumber)
                        .build();
        }

        return null;
    }
}
