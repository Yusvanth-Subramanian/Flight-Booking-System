package com.flightbooking.terzo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTripDTO {

    private long id;

    private String boardingAirport;

    private String landingAirport;

    private Date takeOffTime;

    private Date landingTime;

    private double businessClassSeatCost;

    private double economicClassSeatCost;

    private int businessClassSeatsAvailable;

    private int economicClassSeatsAvailable;

    private long flightId;

    private String flightName;

    private List<Integer> seatsNotAvailable;

}
