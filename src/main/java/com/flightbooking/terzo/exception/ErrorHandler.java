package com.flightbooking.terzo.exception;

import com.flightbooking.terzo.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BookingSessionExpiredException.class)
    public ResponseEntity<Object> bookingSessionExpiredException(){
        return ResponseHandler.generateResponse("Booking session expired !", HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(InsufficientSeatsException.class)
    public ResponseEntity<Object> insufficientSeatsException(){
        return ResponseHandler.generateResponse("There isnt enough seats for your booking",HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<Object> seatAlreadyBookedException(){
        return ResponseHandler.generateResponse("Requested seat is already booked .",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Object> bookingNotFoundException(){
        return ResponseHandler.generateResponse("There is no booking with the given id",HttpStatus.BAD_REQUEST);
    }

}
