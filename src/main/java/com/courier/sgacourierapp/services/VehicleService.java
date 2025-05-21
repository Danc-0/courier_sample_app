package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.entities.VehicleEntity;
import com.courier.sgacourierapp.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;


}
