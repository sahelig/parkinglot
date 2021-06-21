package com.saheli.parkinglot.mapper;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.enums.VehicleType;
import com.saheli.parkinglot.request.ParkRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(value = SpringExtension.class)
@ContextConfiguration(classes = {VehicleMapperImpl.class})
public class VehicleMapperTest {

    @Autowired
    VehicleMapper vehicleMapper;

    @Test
    public void testMappingFromIncomingRequest_Car() {

        ParkRequest parkRequest = new ParkRequest();
        parkRequest.setLicensePlate("1234");
        parkRequest.setType(VehicleType.CAR);

        Vehicle vehicle = vehicleMapper.fromCarParkRequest(parkRequest);

        assertEquals("1234", vehicle.getLicenceNumber());
        assertEquals(new ArrayList<>(), vehicle.getCurrentlyParkedAt());
        assertEquals(2, vehicle.getSlotOptions().size());
        assertEquals(-1, vehicle.getCurrentLevel());
        assertEquals(VehicleType.CAR, vehicle.getVehicleType());

    }

    @Test
    public void testMappingFromIncomingRequest_Bus() {

        ParkRequest parkRequest = new ParkRequest();
        parkRequest.setLicensePlate("1234");
        parkRequest.setType(VehicleType.BUS);

        Vehicle vehicle = vehicleMapper.fromCarParkRequest(parkRequest);

        assertEquals("1234", vehicle.getLicenceNumber());
        assertEquals(new ArrayList<>(), vehicle.getCurrentlyParkedAt());
        assertEquals(1, vehicle.getSlotOptions().size());
        assertEquals(-1, vehicle.getCurrentLevel());
        assertEquals(VehicleType.BUS, vehicle.getVehicleType());

    }

    @Test
    public void testMappingFromIncomingRequest_Bike() {

        ParkRequest parkRequest = new ParkRequest();
        parkRequest.setLicensePlate("1234");
        parkRequest.setType(VehicleType.MOTORCYCLE);

        Vehicle vehicle = vehicleMapper.fromCarParkRequest(parkRequest);

        assertEquals("1234", vehicle.getLicenceNumber());
        assertEquals(new ArrayList<>(), vehicle.getCurrentlyParkedAt());
        assertEquals(3, vehicle.getSlotOptions().size());
        assertEquals(-1, vehicle.getCurrentLevel());
        assertEquals(VehicleType.MOTORCYCLE, vehicle.getVehicleType());

    }

}