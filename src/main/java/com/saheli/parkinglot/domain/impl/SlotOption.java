package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SlotOption {

    int numOfSlots;
    ParkingSpotCategory category;
}
