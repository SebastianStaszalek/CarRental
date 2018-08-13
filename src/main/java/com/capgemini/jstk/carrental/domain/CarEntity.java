package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "CAR")
public class CarEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String brand;

    @Column(nullable = false, length = 20)
    private String model;

    @ManyToOne
    private TypeEntity carType;

    @Column(name = "production_year", nullable = false)
    private int productionYear;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(name = "engine_capacity", nullable = false)
    private int engineCapacity;

    @Column(nullable = false)
    private int power;

    @Column(nullable = false)
    private int milage;

    @ManyToOne
    private LocationEntity currentLocation;

    @OneToMany(mappedBy = "car")
    Set<RentalEntity> rentals;

    @ManyToMany
    @JoinTable(name = "CAR_CARER",
            joinColumns = {@JoinColumn(name = "CAR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID")}
    )
    private Collection<EmployeeEntity> carers;



}
