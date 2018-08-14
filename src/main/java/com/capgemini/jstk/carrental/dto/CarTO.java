package com.capgemini.jstk.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarTO {

    private Long id;
    private String brand;
    private String model;
    private String carType;
    private int productionYear;
    private String color;
    private int engineCapacity;
    private int power;
    private int mileage;
}
