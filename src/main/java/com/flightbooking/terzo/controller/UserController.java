package com.flightbooking.terzo.controller;

import com.flightbooking.terzo.dto.BookFlightDTO;
import com.flightbooking.terzo.dto.GetTripDTO;
import com.flightbooking.terzo.exception.BookingNotFoundException;
import com.flightbooking.terzo.exception.BookingSessionExpiredException;
import com.flightbooking.terzo.exception.InsufficientSeatsException;
import com.flightbooking.terzo.exception.SeatAlreadyBookedException;
import com.flightbooking.terzo.handler.ResponseHandler;
import com.flightbooking.terzo.service.BookingService;
import com.flightbooking.terzo.service.FlightService;
import com.flightbooking.terzo.service.TripService;
import com.flightbooking.terzo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    FlightService flightService;

    TripService tripService;

    BookingService bookingService;

    UserService userService;

    @Autowired
    public UserController(FlightService flightService, TripService tripService,BookingService bookingService,
                          UserService userService){
        this.tripService = tripService;
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.userService = userService;
    }


    @GetMapping("get-trips")
    public ResponseEntity<Object> getTrips(
            @RequestParam("setOff")int setOff,
            @RequestParam("tripPerPage")int tripPerPage,
            @RequestParam("boardingAirport")String boardingAirport,
            @RequestParam("landingAirport")String landingAirport,
            @RequestParam("noOfAdults")int noOfAdults,
            @RequestParam("noOfChildren")int noOfChildren,
            @RequestParam(value = "fromTime",required = false)Date startTime,
            @RequestParam(value = "endTime",required = false)Date endTime,
            @RequestParam(value = "cost",required = false,defaultValue = "0")double cost,
            @RequestParam(value = "noOfWindowSeats",required = false)int noOfWindowSeats,
            @RequestParam(value = "noOfSeatsCloseToEmergencyExit",required = false)int noOfSeatsCloseToEmergencyExit
            ){
        List<GetTripDTO> trips = tripService.getTrips(setOff,tripPerPage,noOfAdults,noOfChildren,
                boardingAirport,landingAirport,startTime,endTime,cost,noOfWindowSeats,noOfSeatsCloseToEmergencyExit);
        return ResponseHandler.generateResponse(trips,"retrieved all trips", HttpStatus.OK);
    }

    @PostMapping("/book-flight")
    public ResponseEntity<Object> bookFlight(@RequestBody BookFlightDTO bookFlightDTO, HttpServletRequest request) throws InsufficientSeatsException, SeatAlreadyBookedException {
        return ResponseHandler.generateResponse(tripService.book(bookFlightDTO,request),"Booking details",HttpStatus.OK);
    }

    @GetMapping("/confirm-booking")
    public ResponseEntity<Object> confirmBooking(@RequestParam("id")long id) throws BookingSessionExpiredException, BookingNotFoundException {
        bookingService.confirmBooking(id);
        return ResponseHandler.generateResponse("Flight booked",HttpStatus.OK);
    }

    @DeleteMapping("/cancel-booking")
    public ResponseEntity<Object> cancelBooking(@RequestParam("id")long id) throws BookingNotFoundException {
        bookingService.cancelBooking(id);
        return ResponseHandler.generateResponse("Booking deleted",HttpStatus.OK);
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<Object> getUserBookingDetails(HttpServletRequest request){
        return ResponseHandler.generateResponse(userService.getUserBookingDetails(request),"User booking history retrieved",HttpStatus.OK);
    }
}
