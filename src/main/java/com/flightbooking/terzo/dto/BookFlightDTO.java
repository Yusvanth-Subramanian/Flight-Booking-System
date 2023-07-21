package com.flightbooking.terzo.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookFlightDTO {

    private long tripId;

    private List<PassengerDetailsDTO> passengerDetailsDTOS;
}
