package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "LOCATION")
public class LocationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "phone", nullable = false)
    private int phoneNumber;

    @Embedded
    private AddressEntity address;

    @OneToMany(mappedBy = "location")
    Set<EmployeeEntity> employess;

    @OneToMany(mappedBy = "startLocation")
    Set<RentalEntity> startRentals;

    @OneToMany(mappedBy = "endLocation")
    Set<RentalEntity> endRentals;

    @OneToMany(mappedBy = "currentLocation")
    Set<CarEntity> cars;
}
