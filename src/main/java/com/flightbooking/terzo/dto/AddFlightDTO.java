package com.flightbooking.terzo.dto;

import lombok.Data;

@Data
public class AddFlightDTO {

    private String name;

    private int totalBusinessClassSeats;

    private int totalEconomicClassSeats;

    private String businessClassSeatsArrangement;

    private String seatsCloseToEmergencyExit;
}
