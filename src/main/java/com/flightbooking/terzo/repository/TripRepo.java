package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepo extends JpaRepository<Trip,Long> {

    Trip findById(long id);

    void deleteById(long id);

    @Query("select t from Trip t where t.boardingAirport = :boardingAirport and t.landingAirport = :landingAirPort")
    List<Trip> getTripsFromBoardingAirportToLandingAirport(String boardingAirport, String landingAirPort);
}
