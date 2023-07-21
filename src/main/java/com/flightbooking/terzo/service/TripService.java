package com.flightbooking.terzo.service;

import com.flightbooking.terzo.dto.*;
import com.flightbooking.terzo.entity.Trip;
import com.flightbooking.terzo.exception.InsufficientSeatsException;
import com.flightbooking.terzo.exception.SeatAlreadyBookedException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

public interface TripService {

    void addNewTrip(AddTripDTO addTripDTO);
    
    void updateTrip(UpdateTripDTO updateTripDTO);

    void deleteTripById(long id);

    List<GetTripDTO> getTrips();

    List<GetTripDTO> getTrips(int setOff,int tripPerPage,int noOfAdults,int noOfChildren,String boardingAirport, String landingAirPort, Date startTime,Date endTime, double cost,int noOfWindowSeats,int noOfSeatsCloseToEmergencyExit);

    GetBookingDetailsDTO book(BookFlightDTO bookFlightDTO, HttpServletRequest request) throws InsufficientSeatsException, SeatAlreadyBookedException;

    void save(Trip trip);
}
