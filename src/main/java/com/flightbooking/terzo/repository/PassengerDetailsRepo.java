package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.PassengerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerDetailsRepo extends JpaRepository<PassengerDetails,Long> {
}
