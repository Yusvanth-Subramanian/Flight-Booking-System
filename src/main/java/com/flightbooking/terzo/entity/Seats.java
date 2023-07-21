package com.flightbooking.terzo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int seatNumber;

    private boolean businessClassSeat;

    private boolean blocked;

    @ManyToOne
    private Flight flight;

    @ManyToOne
    private Trip trip;

    @OneToOne
    private PassengerDetails passengerDetails;

    @ManyToOne
    private BookingDetails bookingDetails;
}
