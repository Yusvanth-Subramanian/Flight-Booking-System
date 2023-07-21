package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.dto.GetBookingDetailsDTO;
import com.flightbooking.terzo.dto.PassengerDetailsDTO;
import com.flightbooking.terzo.dto.RegisterDTO;
import com.flightbooking.terzo.entity.Address;
import com.flightbooking.terzo.entity.BookingDetails;
import com.flightbooking.terzo.entity.PassengerDetails;
import com.flightbooking.terzo.entity.User;
import com.flightbooking.terzo.repository.AddressRepo;
import com.flightbooking.terzo.repository.RolesRepo;
import com.flightbooking.terzo.repository.UserRepo;
import com.flightbooking.terzo.service.UserService;
import com.flightbooking.terzo.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    RolesRepo rolesRepo;

    AddressRepo addressRepo;

    JwtUtils jwtUtils;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, AddressRepo addressRepo,RolesRepo rolesRepo,JwtUtils jwtUtils,
    PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.rolesRepo = rolesRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void registerUser(RegisterDTO registerDTO) {
        User user = new User();
        Address address = new Address();
        BeanUtils.copyProperties(registerDTO,user);
        BeanUtils.copyProperties(registerDTO,address);
        user.setRole(rolesRepo.findByName("USER"));
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepo.save(user);
        address.setUser(user);
        addressRepo.save(address);
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return userRepo.findByEmail(jwtUtils.extractUserName(authorization.substring(7)));
    }

    @Override
    public List<GetBookingDetailsDTO> getUserBookingDetails(HttpServletRequest request) {
        List<BookingDetails> bookingDetails = getCurrentUser(request).getBookingDetails();
        return bookingDetails.stream().map(this::mapToGetBookingDetailsDTO).toList();
    }


    private GetBookingDetailsDTO mapToGetBookingDetailsDTO(BookingDetails bookingDetails){
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
