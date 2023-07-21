package com.flightbooking.terzo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookingDetailsDTO {


    private long tripId;

    private String flightName;

    private double totalCost;

    private List<PassengerDetailsDTO> passengerDetailsDTOS;

    private long bookingDetailsId;
}
