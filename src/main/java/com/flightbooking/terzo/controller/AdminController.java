package com.flightbooking.terzo.controller;

import com.flightbooking.terzo.dto.AddFlightDTO;
import com.flightbooking.terzo.dto.AddTripDTO;
import com.flightbooking.terzo.dto.UpdateFlightDTO;
import com.flightbooking.terzo.dto.UpdateTripDTO;
import com.flightbooking.terzo.handler.ResponseHandler;
import com.flightbooking.terzo.service.FlightService;
import com.flightbooking.terzo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    FlightService flightService;

    TripService tripService;

    @Autowired
    public AdminController(FlightService flightService, TripService tripService){
        this.flightService = flightService;
        this.tripService = tripService;
    }


    @PostMapping("/flight")
    public ResponseEntity<Object> addFlight(@RequestBody AddFlightDTO addFlightDTO){
        flightService.addNewFlight(addFlightDTO);
        return ResponseHandler.generateResponse("Flight added", HttpStatus.OK);
    }

    @PostMapping("/trip")
    public ResponseEntity<Object> addTrip(@RequestBody AddTripDTO addTripDTO){
        tripService.addNewTrip(addTripDTO);
        return ResponseHandler.generateResponse("Trip added",HttpStatus.OK);
    }

    @PutMapping("/flight")
    public ResponseEntity<Object> updateFlight(@RequestBody UpdateFlightDTO updateFlightDTO){
        flightService.updateFlight(updateFlightDTO);
        return ResponseHandler.generateResponse("Flight updated",HttpStatus.OK);
    }

    @PutMapping("/trip")
    public ResponseEntity<Object> updateTrip(@RequestBody UpdateTripDTO updateTripDTO){
        tripService.updateTrip(updateTripDTO);
        return ResponseHandler.generateResponse("Trip updated",HttpStatus.OK);
    }

    @DeleteMapping("/flight")
    public ResponseEntity<Object> deleteFlight(@RequestParam("id")long id){
        flightService.deleteFlightById(id);
        return ResponseHandler.generateResponse("Flight deleted",HttpStatus.OK);
    }

    @DeleteMapping("/trip")
    public ResponseEntity<Object> deleteTrip(@RequestParam("id")long id){
        tripService.deleteTripById(id);
        return ResponseHandler.generateResponse("Trip deleted",HttpStatus.OK);
    }

    @GetMapping("/flight")
    public ResponseEntity<Object> getFlights(){
        return ResponseHandler.generateResponse(flightService.getFlights(),"Flights retrieved",HttpStatus.OK);
    }

    @GetMapping("/trip")
    public ResponseEntity<Object> getTrips(){
        return ResponseHandler.generateResponse(tripService.getTrips(),"Trips retrieved",HttpStatus.OK);
    }

}
