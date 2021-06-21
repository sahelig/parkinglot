package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
public class ParkingLevelTest {

    private ParkingLevel parkingLevel;

    @BeforeEach
    public void init() {
        parkingLevel = new ParkingLevel(1);
    }

    @Test
    public void findParkingSlotInNextRow() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, false, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);


        ParkingSpot startOfDesiredSlot = parkingLevel.startOfContiguousSlotsAvailableOnLevel(2, ParkingSpotCategory
                .COMPACT_SPOT);

        assertEquals(parkingSpotCar1, startOfDesiredSlot);
    }

    @Test
    public void findParkingSlotInFirstRow() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, false, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);


        ParkingSpot startOfDesiredSlot = parkingLevel.startOfContiguousSlotsAvailableOnLevel(1, ParkingSpotCategory.MOTORCYCLE_SPOT);

        assertEquals(parkingSpotBike, startOfDesiredSlot);
    }

    @Test
    public void findParkingSlotInNextRow_OneSlotOnly() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, false, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);


        ParkingSpot startOfDesiredSlot = parkingLevel.startOfContiguousSlotsAvailableOnLevel(1, ParkingSpotCategory.COMPACT_SPOT);

        assertEquals(parkingSpotCar, startOfDesiredSlot);
    }

    @Test
    public void findParkingSlotInNextRow_NoAvailableSlot() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, true, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, true, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);


        ParkingSpot startOfDesiredSlot = parkingLevel
                .startOfContiguousSlotsAvailableOnLevel(2, ParkingSpotCategory.COMPACT_SPOT);

        assertNull(startOfDesiredSlot);
    }

    @Test
    public void findParkingSlotInNextRow_Bus() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotLarge00 = new LargeParkingSpot(0, 4, false, null);
        ParkingSpot parkingSpotLarge01 = new LargeParkingSpot(0, 5, false, null);
        ParkingSpot parkingSpotLarge02 = new LargeParkingSpot(0, 6, false, null);
        ParkingSpot parkingSpotLarge03 = new LargeParkingSpot(0, 7, false, null);
        ParkingSpot parkingSpotLarge1 = new LargeParkingSpot(1, 9, false, null);
        ParkingSpot parkingSpotLarge2 = new LargeParkingSpot(1, 10, false, null);
        ParkingSpot parkingSpotLarge3 = new LargeParkingSpot(1, 11, false, null);
        ParkingSpot parkingSpotLarge4 = new LargeParkingSpot(1, 12, false, null);
        ParkingSpot parkingSpotLarge5 = new LargeParkingSpot(1, 13, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge00);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge01);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge02);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge03);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge2);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge3);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge4);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotLarge5);


        ParkingSpot startOfDesiredSlot = parkingLevel.startOfContiguousSlotsAvailableOnLevel(4, ParkingSpotCategory.LARGE_SPOT);

        assertEquals(parkingSpotLarge00, startOfDesiredSlot);

        startOfDesiredSlot = parkingLevel.startOfContiguousSlotsAvailableOnLevel(5, ParkingSpotCategory.LARGE_SPOT);

        assertEquals(parkingSpotLarge1, startOfDesiredSlot);
    }

    @Test
    public void ParkVehicleTest() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, false, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);


        Vehicle vehicle = new Car();
        vehicle.setLicenceNumber("firstCar");


        parkingLevel.parkVehicle(vehicle);

        Map<String, Vehicle> vehiclesOnFloorCurrently = parkingLevel.getVehiclesOnFloorCurrently();

        assertTrue(vehiclesOnFloorCurrently.containsKey(vehicle.getLicenceNumber()));

        assertTrue(parkingSpotCar.isOccupied());
        assertEquals("firstCar", parkingSpotCar.getLicenseOfVehicleParked());
        assertFalse(parkingSpotBike.isOccupied());
        assertFalse(parkingSpotCar1.isOccupied());
        assertFalse(parkingSpotCar2.isOccupied());

        Vehicle bike1 = new Motorcycle();
        bike1.setLicenceNumber("firstBike");

        Vehicle car2 = new Car();
        car2.setLicenceNumber("secondCar");

        Vehicle bike2 = new Motorcycle();
        bike2.setLicenceNumber("secondBike");

        parkingLevel.parkVehicle(bike1);
        parkingLevel.parkVehicle(car2);
        parkingLevel.parkVehicle(bike2);

        vehiclesOnFloorCurrently = parkingLevel.getVehiclesOnFloorCurrently();

        assertTrue(vehiclesOnFloorCurrently.containsKey(bike1.getLicenceNumber()));
        assertTrue(vehiclesOnFloorCurrently.containsKey(bike2.getLicenceNumber()));
        assertTrue(vehiclesOnFloorCurrently.containsKey(vehicle.getLicenceNumber()));
        assertTrue(vehiclesOnFloorCurrently.containsKey(car2.getLicenceNumber()));

        assertTrue(parkingSpotBike.isOccupied());
        assertEquals("firstBike", parkingSpotBike.getLicenseOfVehicleParked());
        assertTrue(parkingSpotCar.isOccupied());
        assertEquals("firstCar", parkingSpotCar.getLicenseOfVehicleParked());
        assertTrue(parkingSpotCar1.isOccupied());
        assertEquals("secondCar", parkingSpotCar1.getLicenseOfVehicleParked());
        assertTrue(parkingSpotCar2.isOccupied());
        assertEquals("secondBike", parkingSpotCar2.getLicenseOfVehicleParked());

    }

    @Test
    public void locateAndRemoveVehicleTest_VehicleNotOnFloor() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, true, "123");
        ParkingSpot parkingSpotCar = new CompactParkingSpot(1, 4, true, "456");
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, true, "789");
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, true, "101");

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);

        assertFalse(parkingLevel.locateAndRemoveVehicle("999"));
    }

    @Test
    public void locateAndRemoveVehicleTest_VehicleOnFloor() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        ParkingSpot parkingSpotCar1 = new CompactParkingSpot(1, 6, false, null);
        ParkingSpot parkingSpotCar2 = new CompactParkingSpot(1, 7, false, null);

        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar1);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotCar2);

        Vehicle bike1 = new Motorcycle();
        bike1.setLicenceNumber("123");

        Vehicle car2 = new Car();
        car2.setLicenceNumber("789");

        Vehicle bike2 = new Motorcycle();
        bike2.setLicenceNumber("101");

        parkingLevel.parkVehicle(bike1);
        parkingLevel.parkVehicle(car2);
        parkingLevel.parkVehicle(bike2);

        assertTrue(parkingLevel.locateAndRemoveVehicle("123"));
        assertTrue(parkingLevel.locateAndRemoveVehicle("789"));
        assertTrue(parkingLevel.locateAndRemoveVehicle("101"));
        assertFalse(parkingLevel.locateAndRemoveVehicle("123"));
        assertFalse(parkingLevel.locateAndRemoveVehicle("789"));
        assertFalse(parkingLevel.locateAndRemoveVehicle("101"));
    }

    @Test
    public void testDisplay() {
        ParkingSpot parkingSpotBike = new MotorcycleParkingSpot(0, 1, false, null);
        parkingLevel.getParkingSpotsForFloor().add(parkingSpotBike);

        assertNotNull(parkingLevel.toString());
    }

}