package com.flightbooking.terzo.controller;

import com.flightbooking.terzo.dto.AuthenticationResponseDTO;
import com.flightbooking.terzo.dto.LoginDTO;
import com.flightbooking.terzo.dto.RegisterDTO;
import com.flightbooking.terzo.entity.User;
import com.flightbooking.terzo.handler.ResponseHandler;
import com.flightbooking.terzo.service.UserService;
import com.flightbooking.terzo.service.impl.LoginService;
import com.flightbooking.terzo.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    UserService userService;

    LoginService loginService;

    JwtUtils jwtUtils;

    public AuthController(UserService userService, LoginService loginService,JwtUtils jwtUtils) {
        this.userService = userService;
        this.loginService = loginService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {

        User user = userService.findUserByEmail(loginDTO.getEmail());
        if(user==null){
            return ResponseHandler.generateResponse("User not found , please sign up again!!!", HttpStatus.BAD_REQUEST);
        }

        AuthenticationResponseDTO responseDto = loginService.authenticate(loginDTO);

        if (responseDto != null)
            return ResponseHandler.generateResponse( responseDto,"Login Successful", HttpStatus.OK);
        else
            return ResponseHandler.generateResponse("Try again",HttpStatus.EXPECTATION_FAILED);

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO){
        userService.registerUser(registerDTO);
        return ResponseHandler.generateResponse("User saved",HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        jwtUtils.logout(request);
        return ResponseHandler.generateResponse("User logged out",HttpStatus.OK);
    }
}
