package com.flightbooking.terzo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerDetailsDTO {

    private String firstName;

    private String lastName;

    private String adhaarNumber;

    private String gender;

    private boolean isAdult;

    private int seatNumber;

}
