package com.flightbooking.terzo.service;

import com.flightbooking.terzo.entity.BookingDetails;
import com.flightbooking.terzo.exception.BookingNotFoundException;
import com.flightbooking.terzo.exception.BookingSessionExpiredException;

import java.util.List;

public interface BookingService {

    void save(BookingDetails bookingDetails);

    void confirmBooking(long id) throws BookingSessionExpiredException, BookingNotFoundException;

    void cancelBooking(long id) throws BookingNotFoundException;

    List<BookingDetails> findExpiredBookings();
}
