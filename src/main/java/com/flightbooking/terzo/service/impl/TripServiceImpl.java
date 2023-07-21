package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.dto.*;
import com.flightbooking.terzo.entity.*;
import com.flightbooking.terzo.exception.InsufficientSeatsException;
import com.flightbooking.terzo.exception.SeatAlreadyBookedException;
import com.flightbooking.terzo.repository.TripRepo;
import com.flightbooking.terzo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TripServiceImpl implements TripService {

    TripRepo tripRepo;

    FlightService flightService;

    SeatService seatService;

    PassengerService passengerService;

    BookingService bookingService;

    UserService userService;

    @Autowired
    public TripServiceImpl(TripRepo tripRepo, FlightService flightService, SeatService seatService,
                           PassengerService passengerService, BookingService bookingService, UserService userService) {
        this.tripRepo = tripRepo;
        this.flightService = flightService;
        this.seatService = seatService;
        this.passengerService = passengerService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @Override
    public void addNewTrip(AddTripDTO addTripDTO) {
        Trip newTrip = new Trip();
        BeanUtils.copyProperties(addTripDTO, newTrip);
        newTrip.setFlight(flightService.getFlightById(addTripDTO.getFlightID()));
        tripRepo.save(newTrip);
    }

    @Override
    public void updateTrip(UpdateTripDTO updateTripDTO) {
        Trip trip = tripRepo.findById(updateTripDTO.getId());
        BeanUtils.copyProperties(updateTripDTO, trip);
        trip.setFlight(flightService.getFlightById(updateTripDTO.getFlightID()));
        tripRepo.save(trip);
    }

    @Override
    public void deleteTripById(long id) {
        tripRepo.deleteById(id);
    }

    @Override
    public List<GetTripDTO> getTrips() {
        return tripRepo.findAll().stream()
                .map(this::mapToGetTripDTO).collect(Collectors.toList());
    }

    @Override
    public List<GetTripDTO> getTrips(int setOff, int tripPerPage, int noOfAdults, int noOfChildren
            , String boardingAirport, String landingAirPort, Date startTime, Date endTime, double cost,
            int noOfWindowSeats,int noOfSeatsCloseToEmergencyExit) {

        List<Trip> trips = tripRepo.getTripsFromBoardingAirportToLandingAirport(boardingAirport, landingAirPort);

        trips = trips.stream()
                .filter(trip -> trip.getBusinessClassSeatsAvailable() + trip.getEconomicClassSeatsAvailable() >= noOfAdults + noOfChildren)
                .filter(trip -> startTime == null || endTime == null || (trip.getTakeOffTime().after(startTime) && trip.getTakeOffTime().before(endTime)))
                .filter(trip -> cost == 0 || trip.getEconomicClassSeatCost() <= cost)
                .filter(trip -> noOfWindowSeats == 0 || tripHasRequestedNoOfWindowSeats(trip,noOfWindowSeats))
                .filter(trip -> noOfSeatsCloseToEmergencyExit==0||tripHasRequestedSeatsCloseToEmergencyExit(trip,noOfSeatsCloseToEmergencyExit))
                .toList();

        int totalTrips = trips.size();
        int startIndex = (setOff - 1) * tripPerPage;
        int endIndex = Math.min(startIndex + tripPerPage, totalTrips);

        if (endIndex > trips.size())
            trips = trips.subList(startIndex, endIndex);

        return trips.stream()
                .map(this::mapToGetTripDTO)
                .toList();
    }

    private boolean tripHasRequestedSeatsCloseToEmergencyExit(Trip trip, int noOfSeatsCloseToEmergencyExit) {
        List<Integer> bookedSeats = trip.getSeats().stream().map(Seats::getSeatNumber).toList();
        Flight flight = trip.getFlight();
        List<Integer> seatNearEmergencyExit = Arrays.stream(flight.getSeatsCloseToEmergencyExit().split(" ")).map(Integer::valueOf).toList();
        int totalBusinessClassSeats = trip.getBusinessClassSeatsAvailable();
        List<Integer> seatsCloseToEmergencyExit = new ArrayList<>();
        for(int seat:seatNearEmergencyExit){
            if(seat<=totalBusinessClassSeats){
                List<Integer> seatArrangementOfBusinessClass =
                        Arrays.stream(flight.getBusinessClassSeatsArrangement().split(" "))
                                .map(Integer::valueOf).toList();
                int sum = seatArrangementOfBusinessClass.stream().mapToInt(Integer::valueOf).sum();
                if(seat%sum==0){
                    int i=0;
                    while(i<=seatArrangementOfBusinessClass.get(2)){
                        seatsCloseToEmergencyExit.add(seat-i);
                        i++;
                    }
                    i=seat-i;
                    i-=sum;
                    seatsCloseToEmergencyExit.add(i);
                    seatsCloseToEmergencyExit.add(i+1);
                    i+=sum+sum;
                    if(i<totalBusinessClassSeats){
                        seatsCloseToEmergencyExit.add(i);
                        seatsCloseToEmergencyExit.add(i+1);
                    }
                }
                else{
                    int i=0;
                    while(i<=seatArrangementOfBusinessClass.get(0)){
                        seatsCloseToEmergencyExit.add(seat+i);
                        i++;
                    }
                    i=seat+i;
                    i-=sum;
                    seatsCloseToEmergencyExit.add(i);
                    seatsCloseToEmergencyExit.add(i+1);
                    i+=sum+sum;
                    if(i<totalBusinessClassSeats){
                        seatsCloseToEmergencyExit.add(i);
                        seatsCloseToEmergencyExit.add(i+1);
                    }
                }
            }
            else{
                List<Integer> seatArrangementOfEconomyClass =
                        Arrays.stream(flight.getEconomicClassSeatsArrangement().split(" "))
                                .map(Integer::valueOf).toList();
                int sum = seatArrangementOfEconomyClass.stream().mapToInt(Integer::valueOf).sum();
                if(seat%sum==0){
                    int i=0;
                    while(i<=seatArrangementOfEconomyClass.get(2)){
                        seatsCloseToEmergencyExit.add(seat-i);
                        i++;
                    }
                    i=seat-i;
                    i-=sum;
                    seatsCloseToEmergencyExit.add(i);
                    seatsCloseToEmergencyExit.add(i+1);
                    i+=sum+sum;
                    if(i<totalBusinessClassSeats){
                        seatsCloseToEmergencyExit.add(i);
                        seatsCloseToEmergencyExit.add(i+1);
                    }
                }
                else{
                    int i=0;
                    while(i<=seatArrangementOfEconomyClass.get(0)){
                        seatsCloseToEmergencyExit.add(seat+i);
                        i++;
                    }
                    i=seat+i;
                    i-=sum;
                    seatsCloseToEmergencyExit.add(i);
                    seatsCloseToEmergencyExit.add(i+1);
                    i+=sum+sum;
                    if(i<totalBusinessClassSeats){
                        seatsCloseToEmergencyExit.add(i);
                        seatsCloseToEmergencyExit.add(i+1);
                    }
                }
            }
        }

        return seatsCloseToEmergencyExit.stream()
                .filter(i->!bookedSeats.contains(i)).toList().size()>=noOfSeatsCloseToEmergencyExit;

    }

    private boolean tripHasRequestedNoOfWindowSeats(Trip trip,int noOfWindowSeats) {
        List<Integer> bookedSeats = trip.getSeats().stream().map(Seats::getSeatNumber).toList();
        Flight flight = trip.getFlight();
        int seatsPerRowInBusinessClass = Arrays.stream(flight.getBusinessClassSeatsArrangement().split(" "))
                .mapToInt(Integer::valueOf)
                .sum();

        int seatsPerRowInEconomyClass = Arrays.stream(flight.getEconomicClassSeatsArrangement().split(" "))
                .mapToInt(Integer::valueOf)
                .sum();

        List<Integer> windowSeats = new ArrayList<>();

        int totalBusinessClassSeats = trip.getBusinessClassSeatsAvailable();
        int totalEconomyClassSeats = trip.getEconomicClassSeatsAvailable();
        IntStream.range(1,totalEconomyClassSeats+1)
                .filter(i->i%seatsPerRowInEconomyClass==0||i%seatsPerRowInEconomyClass==1)
                .forEach(windowSeats::add);
        IntStream.range(1,totalBusinessClassSeats+1)
                .filter(i->i%seatsPerRowInBusinessClass==0||i%seatsPerRowInBusinessClass==1)
                .forEach(windowSeats::add);

        return windowSeats.stream()
                .filter(i->!bookedSeats.contains(i)).toList().size()>=noOfWindowSeats;
    }

    @Override
    public synchronized GetBookingDetailsDTO book(BookFlightDTO bookFlightDTO, HttpServletRequest request) throws InsufficientSeatsException, SeatAlreadyBookedException {
        Trip trip = tripRepo.findById(bookFlightDTO.getTripId());
        if (bookFlightDTO.getPassengerDetailsDTOS().size() > trip.getEconomicClassSeatsAvailable() + trip.getBusinessClassSeatsAvailable()) {
            throw new InsufficientSeatsException();
        }
        List<PassengerDetailsDTO> passengerDetailsDTOS = bookFlightDTO.getPassengerDetailsDTOS();
        List<PassengerDetails> passengerDetails = new ArrayList<>();
        List<Seats> selectedSeats = new ArrayList<>();
        int noOfChildren = 0;
        int noOfAdults = 0;
        double cost = 0;
        int totalBusinessClassSeats = 0;
        int totalEconomicClassSeats = 0;
        BookingDetails bookingDetails = new BookingDetails();
        bookingService.save(bookingDetails);
        for (PassengerDetailsDTO passengerDetailsDTO : passengerDetailsDTOS) {
            PassengerDetails passengerDetail = new PassengerDetails();
            passengerService.save(passengerDetail);
            if (seatService.findSeatBySeatNumber(passengerDetailsDTO.getSeatNumber()) != null) {
                throw new SeatAlreadyBookedException();
            }
            Seats seat = Seats.builder()
                    .seatNumber(passengerDetailsDTO.getSeatNumber())
                    .bookingDetails(bookingDetails)
                    .trip(trip)
                    .passengerDetails(passengerDetail)
                    .blocked(false)
                    .build();
            BeanUtils.copyProperties(passengerDetailsDTO, passengerDetail);
            seatService.save(seat);
            selectedSeats.add(seat);
            passengerDetail.setSeat(seat);
            if (passengerDetailsDTO.isAdult())
                noOfAdults++;
            else
                noOfChildren++;

            if (passengerDetailsDTO.getSeatNumber() <= trip.getFlight().getTotalBusinessClassSeats()) {
                seat.setBusinessClassSeat(true);
                totalBusinessClassSeats++;
                cost += trip.getBusinessClassSeatCost();
            } else {
                seat.setBusinessClassSeat(false);
                cost += trip.getEconomicClassSeatCost();
                totalEconomicClassSeats++;
            }
            seatService.save(seat);
            passengerDetail.setBookingDetails(bookingDetails);
            passengerService.save(passengerDetail);
            passengerDetails.add(passengerDetail);
        }

        trip.setBusinessClassSeatsAvailable(trip.getBusinessClassSeatsAvailable() - totalBusinessClassSeats);
        trip.setEconomicClassSeatsAvailable(trip.getEconomicClassSeatsAvailable() - totalEconomicClassSeats);
        tripRepo.save(trip);

        bookingDetails.setPassengerDetails(passengerDetails);
        bookingDetails.setSelectedSeats(selectedSeats);
        bookingDetails.setTrip(trip);
        bookingDetails.setTotalCost(cost);
        bookingDetails.setNumberOfInfants(noOfChildren);
        bookingDetails.setNumberOfAdults(noOfAdults);
        bookingDetails.setSessionExpiryDate(new Date(new Date().getTime() + 10 * 60 * 1000));
        bookingDetails.setStatus("Waiting for Payment");
        bookingDetails.setUser(userService.getCurrentUser(request));
        bookingService.save(bookingDetails);
        return mapToGetBookingDetailsDTO(bookingDetails);
    }

    @Override
    public void save(Trip trip) {
        tripRepo.save(trip);
    }

    private GetTripDTO mapToGetTripDTO(Trip trip) {
        GetTripDTO getTripDTO = new GetTripDTO();
        BeanUtils.copyProperties(tripRepo.findById(trip.getId()), getTripDTO);
        getTripDTO.setFlightName(trip.getFlight().getName());
        getTripDTO.setFlightId(trip.getFlight().getId());

        List<Integer> availableSeats = trip.getSeats().stream().map(Seats::getSeatNumber).toList();
        getTripDTO.setSeatsNotAvailable(availableSeats);

        return getTripDTO;
    }

    private GetBookingDetailsDTO mapToGetBookingDetailsDTO(BookingDetails bookingDetails) {
        GetBookingDetailsDTO getBookingDetailsDTO = new GetBookingDetailsDTO();
        getBookingDetailsDTO.setTripId(bookingDetails.getTrip().getId());
        getBookingDetailsDTO.setFlightName(bookingDetails.getTrip().getFlight().getName());
        getBookingDetailsDTO.setTotalCost(bookingDetails.getTotalCost());
        List<PassengerDetailsDTO> passengerDetailsDTOList = bookingDetails.getPassengerDetails()
                .stream().map(this::mapToPassengerDetailsDTO).toList();
        getBookingDetailsDTO.setPassengerDetailsDTOS(passengerDetailsDTOList);
        getBookingDetailsDTO.setBookingDetailsId(bookingDetails.getId());
        return getBookingDetailsDTO;
    }

    private PassengerDetailsDTO mapToPassengerDetailsDTO(PassengerDetails passengerDetails) {
        return PassengerDetailsDTO.builder()
                .adhaarNumber(passengerDetails.getAdhaarNumber())
                .gender(passengerDetails.getGender())
                .firstName(passengerDetails.getFirstName())
                .lastName(passengerDetails.getLastName())
                .seatNumber(passengerDetails.getSeat().getSeatNumber())
                .isAdult(passengerDetails.isAdult())
                .build();
    }

}
