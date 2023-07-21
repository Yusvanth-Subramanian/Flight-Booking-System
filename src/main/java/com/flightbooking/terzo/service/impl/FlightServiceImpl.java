package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.dto.AddFlightDTO;
import com.flightbooking.terzo.dto.GetFlightDTO;
import com.flightbooking.terzo.dto.UpdateFlightDTO;
import com.flightbooking.terzo.entity.Flight;
import com.flightbooking.terzo.repository.FlightRepo;
import com.flightbooking.terzo.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    FlightRepo flightRepo;

    public FlightServiceImpl(FlightRepo flightRepo){
        this.flightRepo = flightRepo;
    }

    @Override
    public void addNewFlight(AddFlightDTO addFlightDTO) {
        Flight newFlight = new Flight();
        BeanUtils.copyProperties(addFlightDTO,newFlight);
        flightRepo.save(newFlight);
    }

    @Override
    public Flight getFlightById(long id) {
        return flightRepo.findById(id);
    }

    @Override
    public void updateFlight(UpdateFlightDTO updateFlightDTO) {
        Flight flight = flightRepo.findById(updateFlightDTO.getId());
        BeanUtils.copyProperties(updateFlightDTO,flight);
        flightRepo.save(flight);
    }

    @Override
    public void deleteFlightById(long id) {
        flightRepo.deleteById(id);
    }

    @Override
    public List<GetFlightDTO> getFlights() {
        List<Flight> flights = flightRepo.findAll();
        return flights.stream().map(flight -> mapToGetFlightDTO(flight)).toList();
    }

    private GetFlightDTO mapToGetFlightDTO(Flight flight) {
        return GetFlightDTO.builder()
                .id(flight.getId())
                .name(flight.getName())
                .build();
    }
}
