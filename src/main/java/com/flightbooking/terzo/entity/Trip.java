package com.flightbooking.terzo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String boardingAirport;

    private String landingAirport;

    private Date takeOffTime;

    private Date landingTime;

    private double businessClassSeatCost;

    private double economicClassSeatCost;

    private int businessClassSeatsAvailable;

    private int economicClassSeatsAvailable;

    @ManyToOne
    private Flight flight;

    @OneToMany(mappedBy = "trip",cascade = CascadeType.ALL)
    private List<BookingDetails> bookingDetails;

    @OneToMany(mappedBy = "trip",cascade = CascadeType.ALL)
    private List<Seats> seats;
}
