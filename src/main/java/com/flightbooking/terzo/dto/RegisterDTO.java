package com.flightbooking.terzo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private long mobileNumber;

    private String address;

    private String state;

    private String country;

    private long pinCode;
}
