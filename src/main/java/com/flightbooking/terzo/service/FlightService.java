package com.flightbooking.terzo.service;

import com.flightbooking.terzo.dto.AddFlightDTO;
import com.flightbooking.terzo.dto.GetFlightDTO;
import com.flightbooking.terzo.dto.UpdateFlightDTO;
import com.flightbooking.terzo.entity.Flight;

import java.util.List;

public interface FlightService {

    public void addNewFlight(AddFlightDTO addFlightDTO);

    public Flight getFlightById(long id);

    public void updateFlight(UpdateFlightDTO updateFlightDTO);

    void deleteFlightById(long id);

    List<GetFlightDTO> getFlights();
}
