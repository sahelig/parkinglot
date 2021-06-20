package com.saheli.parkinglot.mapper;

import com.saheli.parkinglot.domain.Vehicle;
import com.saheli.parkinglot.domain.impl.Bus;
import com.saheli.parkinglot.domain.impl.Car;
import com.saheli.parkinglot.domain.impl.Motorcycle;
import com.saheli.parkinglot.enums.VehicleType;
import com.saheli.parkinglot.request.ParkRequest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VehicleMapper {

    default Vehicle fromCarParkRequest(ParkRequest request) {
        Vehicle vehicle = null;
        VehicleType type = request.getType();

        switch (type) {
            case BUS:
                vehicle = new Bus();
                vehicle.setLicenceNumber(request.getLicensePlate());
                break;
            case CAR:
                vehicle = new Car();
                vehicle.setLicenceNumber(request.getLicensePlate());
                break;
            case MOTORCYCLE:
                vehicle = new Motorcycle();
                vehicle.setLicenceNumber(request.getLicensePlate());
                break;
        }

        return vehicle;

    }
}
