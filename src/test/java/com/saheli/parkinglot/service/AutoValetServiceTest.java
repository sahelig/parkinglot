package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.domain.impl.Car;
import com.saheli.parkinglot.domain.impl.LargeParkingSpot;
import com.saheli.parkinglot.domain.impl.ParkingLevel;
import com.saheli.parkinglot.exception.ParkingSpaceNotAvailableException;
import com.saheli.parkinglot.exception.VehicleNotFoundException;
import com.saheli.parkinglot.mapper.VehicleMapper;
import com.saheli.parkinglot.request.ParkRequest;
import com.saheli.parkinglot.request.VacateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(value = SpringExtension.class)
public class AutoValetServiceTest {

    @MockBean(name = "level1")
    private ParkingLevel parkingLevel1;

    @MockBean(name = "level2")
    private ParkingLevel parkingLevel2;

    @MockBean
    private VehicleMapper vehicleMapper;

    AutoValetService autoValetService;

    ParkRequest parkRequest;
    private VacateRequest vacateRequest;

    Vehicle vehicle;

    @BeforeEach
    public void init() {
        when(parkingLevel1.getFloorNumber()).thenReturn(1);
        when(parkingLevel2.getFloorNumber()).thenReturn(2);

        parkRequest = new ParkRequest();
        vehicle = new Car();
        when(vehicleMapper.fromCarParkRequest(parkRequest)).thenReturn(vehicle);

        vacateRequest = new VacateRequest();
        vacateRequest.setLicensePlate("abcd");

    }

    @Test
    public void noParkingLevelInTheLot() throws ParkingSpaceNotAvailableException {
        autoValetService = new AutoValetService(Collections.emptyList(), vehicleMapper);
        assertThrows(ParkingSpaceNotAvailableException.class, () -> autoValetService.park(parkRequest));
    }

    @Test
    public void ParkingAvailableInLevel1() throws ParkingSpaceNotAvailableException {
        when(parkingLevel1.parkVehicle(vehicle)).thenReturn(new LargeParkingSpot(1, 1));
        autoValetService = new AutoValetService(Arrays.asList(parkingLevel1, parkingLevel2),
                vehicleMapper);

        String parkedLevelString = autoValetService.park(parkRequest);

        assertEquals(parkingLevel1.toString(), parkedLevelString);
    }

    @Test
    public void ParkingAvailableInLevel2() throws ParkingSpaceNotAvailableException {
        when(parkingLevel1.parkVehicle(vehicle)).thenReturn(null);
        when(parkingLevel2.parkVehicle(vehicle)).thenReturn(new LargeParkingSpot(1, 1));
        autoValetService = new AutoValetService(Arrays.asList(parkingLevel1, parkingLevel2),
                vehicleMapper);

        String parkedLevelString = autoValetService.park(parkRequest);

        assertEquals(parkingLevel2.toString(), parkedLevelString);
    }

    @Test
    public void VacateInvokedButNoParkingLevelInTheLot() throws
            ParkingSpaceNotAvailableException {
        autoValetService = new AutoValetService(Collections.emptyList(), vehicleMapper);
        assertThrows(VehicleNotFoundException.class, () -> autoValetService
                .vacate(vacateRequest));
    }

    @Test
    public void vehicleParkedInLevel1() throws VehicleNotFoundException {
        when(parkingLevel1.locateAndRemoveVehicle("abcd")).thenReturn(true);
        autoValetService = new AutoValetService(Arrays.asList(parkingLevel1, parkingLevel2),
                vehicleMapper);

        String parkedLevelString = autoValetService.vacate(vacateRequest);

        assertEquals(parkingLevel1.toString(), parkedLevelString);
    }

    @Test
    public void vehicleParkedInLevel2() throws VehicleNotFoundException {
        when(parkingLevel1.locateAndRemoveVehicle("abcd")).thenReturn(false);
        when(parkingLevel2.locateAndRemoveVehicle("abcd")).thenReturn(true);

        autoValetService = new AutoValetService(Arrays.asList(parkingLevel1, parkingLevel2),
                vehicleMapper);

        String parkedLevelString = autoValetService.vacate(vacateRequest);

        assertEquals(parkingLevel2.toString(), parkedLevelString);
    }
}