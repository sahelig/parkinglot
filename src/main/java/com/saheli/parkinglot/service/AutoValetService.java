package com.saheli.parkinglot.service;

import com.saheli.parkinglot.domain.ParkingSpot;
import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.domain.impl.ParkingLevel;
import com.saheli.parkinglot.exception.ParkingSpaceNotAvailableException;
import com.saheli.parkinglot.exception.VehicleNotFoundException;
import com.saheli.parkinglot.mapper.VehicleMapper;
import com.saheli.parkinglot.request.ParkRequest;
import com.saheli.parkinglot.request.VacateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AutoValetService {

    private volatile List<ParkingLevel> parkingLevelList;
    private VehicleMapper vehicleMapper;


    @Autowired
    public AutoValetService(List<ParkingLevel> parkingLevelList, VehicleMapper vehicleMapper) {
        this.parkingLevelList = parkingLevelList;
        this.vehicleMapper = vehicleMapper;
    }

    public synchronized String park(ParkRequest parkRequest) throws
            ParkingSpaceNotAvailableException {

        Vehicle vehicle = vehicleMapper.fromCarParkRequest(parkRequest);

        Optional<ParkingLevel> firstAvailableParkingLevel = parkingLevelList.stream().filter(parkingLevel -> {
            ParkingSpot spot = parkingLevel.parkVehicle(vehicle);
            log.info("Parked vehicle={} at spot={} ", spot);
            return spot != null;
        }).findFirst();

        if (!firstAvailableParkingLevel.isPresent()) {
            log.error("No Parking space found for Vehicle={}", vehicle);
            throw new ParkingSpaceNotAvailableException("No parking space left!");
        }

        return firstAvailableParkingLevel.get().toString();
    }

    public synchronized String vacate(VacateRequest parkRequest) throws VehicleNotFoundException {

        Optional<ParkingLevel> firstAvailableParkingLevel = parkingLevelList.stream().filter(
                parkingLevel -> parkingLevel.locateAndRemoveVehicle(parkRequest.getLicensePlate())).findFirst();

        if (!firstAvailableParkingLevel.isPresent()) {
            log.error("No Such Vehicle Found={}", parkRequest.getLicensePlate());
            throw new VehicleNotFoundException("No Such Vehicle Found");
        }

        return firstAvailableParkingLevel.get().toString();
    }
}
