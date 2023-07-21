package com.flightbooking.terzo.service;

import com.flightbooking.terzo.entity.Seats;

public interface SeatService {

    public void save(Seats seat);

    public Seats findSeatBySeatNumber(int seatNumber);

}
