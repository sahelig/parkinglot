package com.saheli.parkinglot.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class ParkingSpotAddRequest {

    @NotNull
    private ParkingSpotCategory category;
    @NotNull
    private int level;
    @NotNull
    private int rowNumber;
    @NotNull
    private int columnNumber;

}
