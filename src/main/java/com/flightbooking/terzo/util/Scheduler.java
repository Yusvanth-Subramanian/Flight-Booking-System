package com.flightbooking.terzo.util;

import com.flightbooking.terzo.entity.BookingDetails;
import com.flightbooking.terzo.exception.BookingNotFoundException;
import com.flightbooking.terzo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Scheduler {

    BookingService bookingService;

    @Autowired
    public Scheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "*/5 * * * *")
    public void deleteExpiredBookings() throws BookingNotFoundException {
        List<BookingDetails> expiredBookings = bookingService.findExpiredBookings();

        for(BookingDetails bookingDetails : expiredBookings){
            bookingService.cancelBooking(bookingDetails.getId());
        }
    }
}
