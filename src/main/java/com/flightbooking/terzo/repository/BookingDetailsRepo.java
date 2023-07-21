package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailsRepo extends JpaRepository<BookingDetails,Long> {

    BookingDetails findById(long id);

    void deleteById(long id);
}
