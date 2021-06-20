package com.saheli.parkinglot.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saheli.parkinglot.enums.VehicleType;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkRequest {

    private VehicleType type;
    private String licensePlate;

}
