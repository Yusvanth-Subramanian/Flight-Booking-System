package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.entity.BookingDetails;
import com.flightbooking.terzo.entity.Seats;
import com.flightbooking.terzo.entity.Trip;
import com.flightbooking.terzo.exception.BookingNotFoundException;
import com.flightbooking.terzo.exception.BookingSessionExpiredException;
import com.flightbooking.terzo.repository.BookingDetailsRepo;
import com.flightbooking.terzo.service.BookingService;
import com.flightbooking.terzo.service.TripService;
import com.flightbooking.terzo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    BookingDetailsRepo bookingDetailsRepo;

    TripService tripService;

    @Lazy
    @Autowired
    public BookingServiceImpl(BookingDetailsRepo bookingDetailsRepo,TripService tripService){
        this.bookingDetailsRepo = bookingDetailsRepo;
        this.tripService = tripService;
    }

    @Override
    public void save(BookingDetails bookingDetails) {
        bookingDetailsRepo.save(bookingDetails);
    }

    @Override
    public synchronized void confirmBooking(long id) throws BookingSessionExpiredException, BookingNotFoundException {
        BookingDetails bookingDetails = bookingDetailsRepo.findById(id);
        if(bookingDetails==null){
            throw new BookingNotFoundException();
        }
        if(bookingDetails.getSessionExpiryDate().before(new Date())){
            throw new BookingSessionExpiredException();
        }
        bookingDetails.setStatus("Booked");
    }

    @Override
    public void cancelBooking(long id) throws BookingNotFoundException {
        BookingDetails bookingDetails = bookingDetailsRepo.findById(id);
        if(bookingDetails==null){
            throw new BookingNotFoundException();
        }
        Trip trip = bookingDetails.getTrip();
        int businessClassSeats = (int) bookingDetails.getSelectedSeats()
                .stream().filter(Seats::isBusinessClassSeat).count();
        trip.setEconomicClassSeatsAvailable(bookingDetails.getSelectedSeats().size()-businessClassSeats);
        trip.setBusinessClassSeatsAvailable(businessClassSeats);
        tripService.save(trip);
        bookingDetailsRepo.deleteById(id);
    }

    @Override
    public List<BookingDetails> findExpiredBookings() {
        return bookingDetailsRepo.findAll()
                .stream()
                .filter(bookingDetails ->
                        bookingDetails.getSessionExpiryDate().before(new Date()) &&
                        bookingDetails.getStatus().equals("Waiting for Payment"))
                .toList();
    }
}
