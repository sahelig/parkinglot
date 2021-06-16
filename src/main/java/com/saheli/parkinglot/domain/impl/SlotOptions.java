package com.saheli.parkinglot.domain.impl;

import com.saheli.parkinglot.enums.ParkingSpotCategory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SlotOptions {

    int numOfSlots;
    ParkingSpotCategory category;
}
