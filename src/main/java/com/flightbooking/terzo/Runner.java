package com.flightbooking.terzo;

import com.flightbooking.terzo.dto.RegisterDTO;
import com.flightbooking.terzo.repository.RolesRepo;
import com.flightbooking.terzo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    UserService userService;


    @Autowired
    public Runner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        RegisterDTO registerDTO = RegisterDTO.builder()
//                .email("yusvanths@gmail.com")
//                .firstName("Yusvanth")
//                .lastName("Subramanian")
//                .country("India")
//                .state("Tamil Nadu")
//                .address("Erode")
//                .password("123")
//                .mobileNumber(1234567)
//                .pinCode(638052)
//                .build();
//        userService.registerUser(registerDTO);
    }
}
