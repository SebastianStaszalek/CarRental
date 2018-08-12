package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CAR")
public class CarEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;
    private String model;

    private TypeEntity type;
    @Column(name = "production_year")
    private int productionYear;
    private String color;
    @Column (name = "engine_capacity")
    private int engineCapacity;
    private int power;
    private int milage;

    @Column (name = "current_location")
    private LocationEntity currentLocation;

}
