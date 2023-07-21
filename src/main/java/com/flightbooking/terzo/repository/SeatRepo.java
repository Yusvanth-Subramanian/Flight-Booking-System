package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepo extends JpaRepository<Seats,Long> {

    Seats findBySeatNumber(int seatNumber);
}
