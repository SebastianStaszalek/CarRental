package com.capgemini.jstk.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private int totalCost;

    private Long customerId;
    private Long carId;
    private Long startLocationId;
    private Long endLocationId;
}
