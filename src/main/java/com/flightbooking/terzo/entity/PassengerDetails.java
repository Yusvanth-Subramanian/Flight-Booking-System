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
public class PassengerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String adhaarNumber;

    private String gender;

    private boolean isAdult;


    @ManyToOne
    private BookingDetails bookingDetails;

    @OneToOne(mappedBy = "passengerDetails",cascade = CascadeType.ALL)
    private Seats seat;
}
