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
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int totalEconomicClassSeats;

    private int totalBusinessClassSeats;

    private String economicClassSeatsArrangement;

    private String businessClassSeatsArrangement;

    private String seatsCloseToEmergencyExit;

    @OneToMany(mappedBy = "flight",cascade = CascadeType.ALL)
    private List<Trip> trips;

    @OneToMany(mappedBy = "flight",cascade = CascadeType.ALL)
    private List<Seats> seats;

}


/*

business class arrangement = 2 3 2

    ∆∆ ∆∆∆ ∆∆
    ∆∆ ∆∆∆ ∆∆                window seat calc =>  seatNumber % 7 = 0 || 1
  e ∆∆ ∆∆∆ ∆∆

economy class arrangement = 3 4 3

    ∆∆∆ ∆∆∆∆ ∆∆∆
    ∆∆∆ ∆∆∆∆ ∆∆∆ e
    ∆∆∆ ∆∆∆∆ ∆∆∆             window seat calc =>  seatNumber % 10 = 0 || 1
    ∆∆∆ ∆∆∆∆ ∆∆∆
    ∆∆∆ ∆∆∆∆ ∆∆∆


a1=[3,4,3], a2=[2,3,2]  ,  emergencyExitSeatNumber as e;

Emergency exit calc =>

            seats = []

            if ( e % (a1[0]+a1[1]+a1[2]) == 0 )
            {
                i = 0;
                while(i <= a1[2])
                {
                    seats.add(e-i);
                    i++;
                }
                i=e-i
                i -= (a1[0]+a1[1]+a1[2])
                seats.add(i,i+1)
                i += (a1[0]+a1[1]+a1[2])^2
                seats.add(i,i+1)
            }
            else
            {
                i = 0;
                while(i <= a2[0])
                {
                    seats.add(e+i);
                    i++;
                }
                i=e+i
                i -= (a2[0]+a2[1]+a2[2])
                seats.add(i,i+1)
                i += (a2[0]+a2[1]+a2[2])^2
                seats.add(i,i+1)

 */