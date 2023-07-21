package com.flightbooking.terzo.service;

import com.flightbooking.terzo.dto.GetBookingDetailsDTO;
import com.flightbooking.terzo.dto.RegisterDTO;
import com.flightbooking.terzo.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    User findUserByEmail(String email);

    void registerUser(RegisterDTO registerDTO);

    User getCurrentUser(HttpServletRequest request);

    List<GetBookingDetailsDTO> getUserBookingDetails(HttpServletRequest request);
}
