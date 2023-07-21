package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.entity.Seats;
import com.flightbooking.terzo.repository.SeatRepo;
import com.flightbooking.terzo.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    SeatRepo seatRepo;

    @Autowired
    public SeatServiceImpl(SeatRepo seatRepo){
        this.seatRepo = seatRepo;
    }

    @Override
    public void save(Seats seat) {
        seatRepo.save(seat);
    }

    @Override
    public Seats findSeatBySeatNumber(int seatNumber) {
        return seatRepo.findBySeatNumber(seatNumber);
    }
}
