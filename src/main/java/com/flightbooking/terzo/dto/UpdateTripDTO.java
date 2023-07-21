package com.flightbooking.terzo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateTripDTO {

    private long id;

    private String boardingAirport;

    private String landingAirport;

    private Date takeOffTime;

    private Date landingTime;

    private double businessClassSeatCost;

    private double economicClassSeatCost;

    private int businessClassSeatsAvailable;

    private int economicClassSeatsAvailable;

    private int flightID;
}
