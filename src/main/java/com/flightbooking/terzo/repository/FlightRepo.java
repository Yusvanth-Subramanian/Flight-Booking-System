package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends JpaRepository<Flight,Long> {

    Flight findById(long id);

    void deleteById(long id);

}
