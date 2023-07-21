package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.entity.PassengerDetails;
import com.flightbooking.terzo.repository.PassengerDetailsRepo;
import com.flightbooking.terzo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {

    PassengerDetailsRepo passengerDetailsRepo;

    @Autowired
    public PassengerServiceImpl(PassengerDetailsRepo passengerDetailsRepo){
        this.passengerDetailsRepo = passengerDetailsRepo;
    }

    @Override
    public void save(PassengerDetails passengerDetails) {
        passengerDetailsRepo.save(passengerDetails);
    }
}
