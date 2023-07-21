package com.flightbooking.terzo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private long mobileNumber;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<BookingDetails> bookingDetails;

    @ManyToOne
    private Role role;

}
