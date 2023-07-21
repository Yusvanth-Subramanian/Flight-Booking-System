package com.flightbooking.terzo.dto;


import lombok.Data;

@Data
public class UpdateFlightDTO {

    private long id;

    private String name;

    private int totalBusinessClassSeats;

    private int totalEconomicClassSeats;
}
