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
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numberOfAdults;

    private int numberOfInfants;

    private String status;

    private Date sessionExpiryDate;

    private double totalCost;

    @ManyToOne
    private User user;

    @ManyToOne
    private Trip trip;

    @OneToMany(mappedBy = "bookingDetails",cascade = CascadeType.ALL)
    private List<Seats> selectedSeats;

    @OneToMany(mappedBy = "bookingDetails",cascade = CascadeType.ALL)
    private List<PassengerDetails> passengerDetails;

}
