package com.saheli.parkinglot.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingSpotAddRequest {

    private ParkingSpotCategory category;
    private int level;
    private int slotNumber;
    private int rowNumber;

}
