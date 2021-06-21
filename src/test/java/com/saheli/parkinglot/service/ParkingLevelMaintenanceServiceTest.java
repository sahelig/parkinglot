package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.impl.ParkingLevel;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import com.saheli.parkinglot.exception.InvalidParkingSlotRequestException;
import com.saheli.parkinglot.exception.ParkingLevelNotAvailableException;
import com.saheli.parkinglot.request.ParkingSpotAddRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(value = SpringExtension.class)
@ContextConfiguration(classes = {ParkingLevelMaintenanceService.class})
public class ParkingLevelMaintenanceServiceTest {

    ParkingLevelMaintenanceService parkingLevelMaintenanceService;

    @BeforeEach
    public void init() {
        ParkingLevel parkingLevel = new ParkingLevel(1);
        parkingLevelMaintenanceService = new ParkingLevelMaintenanceService(Arrays
                .asList(parkingLevel));
    }

    @Test
    public void testAddSlot_DuplicateThrowsException() throws
            InvalidParkingSlotRequestException, ParkingLevelNotAvailableException {
        ParkingSpotAddRequest parkingSpotAddRequest = new ParkingSpotAddRequest();
        parkingSpotAddRequest.setCategory(ParkingSpotCategory.LARGE_SPOT);
        parkingSpotAddRequest.setColumnNumber(10);
        parkingSpotAddRequest.setRowNumber(1);
        parkingSpotAddRequest.setLevel(1);

        parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest);

        assertThrows(InvalidParkingSlotRequestException.class, () ->
                parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest));
    }

    @Test
    public void testAddSlot_LevelNotPresent() throws
            InvalidParkingSlotRequestException, ParkingLevelNotAvailableException {
        ParkingSpotAddRequest parkingSpotAddRequest = new ParkingSpotAddRequest();
        parkingSpotAddRequest.setCategory(ParkingSpotCategory.LARGE_SPOT);
        parkingSpotAddRequest.setColumnNumber(10);
        parkingSpotAddRequest.setRowNumber(1);
        parkingSpotAddRequest.setLevel(2);

        assertThrows(ParkingLevelNotAvailableException.class, () ->
                parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest));
    }

    @Test
    public void testAddSlot_SlotsAutoOrdering() throws
            InvalidParkingSlotRequestException, ParkingLevelNotAvailableException {

        ParkingSpotAddRequest parkingSpotAddRequest2 = new ParkingSpotAddRequest();
        parkingSpotAddRequest2.setCategory(ParkingSpotCategory.COMPACT_SPOT);
        parkingSpotAddRequest2.setColumnNumber(10);
        parkingSpotAddRequest2.setRowNumber(1);
        parkingSpotAddRequest2.setLevel(1);

        ParkingSpotAddRequest parkingSpotAddRequest = new ParkingSpotAddRequest();
        parkingSpotAddRequest.setCategory(ParkingSpotCategory.LARGE_SPOT);
        parkingSpotAddRequest.setColumnNumber(8);
        parkingSpotAddRequest.setRowNumber(1);
        parkingSpotAddRequest.setLevel(1);

        ParkingSpotAddRequest parkingSpotAddRequest1 = new ParkingSpotAddRequest();
        parkingSpotAddRequest1.setCategory(ParkingSpotCategory.MOTORCYCLE_SPOT);
        parkingSpotAddRequest1.setColumnNumber(7);
        parkingSpotAddRequest1.setRowNumber(0);
        parkingSpotAddRequest1.setLevel(1);


        parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest2);
        parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest);
        parkingLevelMaintenanceService.addSpot(parkingSpotAddRequest1);


        List<ParkingSpot> parkingSpotsForFloor = parkingLevelMaintenanceService.getParkingLevelList().get(0).getParkingSpotsForFloor();
        assertEquals(parkingSpotAddRequest1.getColumnNumber(), parkingSpotsForFloor.get(0).getColumn());
        assertEquals(parkingSpotAddRequest1.getRowNumber(), parkingSpotsForFloor.get(0)
                .getRowNumber());
        assertEquals(parkingSpotAddRequest.getColumnNumber(), parkingSpotsForFloor.get(1).getColumn());
        assertEquals(parkingSpotAddRequest.getRowNumber(), parkingSpotsForFloor.get(1).getRowNumber());
        assertEquals(parkingSpotAddRequest2.getColumnNumber(), parkingSpotsForFloor.get(2).getColumn());
        assertEquals(parkingSpotAddRequest2.getRowNumber(), parkingSpotsForFloor.get(2).getRowNumber());
    }


}