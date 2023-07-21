package com.flightbooking.terzo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFlightDTO {

    private long id;

    private String name;


}
