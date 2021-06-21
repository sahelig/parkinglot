package com.saheli.parkinglot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = SpringExtension.class)
@ContextConfiguration(classes = {ParkingLevelMaintenanceService.class})
public class ParkingLevelMaintenanceServiceTest {

    @Autowired
    ParkingLevelMaintenanceService parkingLevelMaintenanceService;

    @Test

}