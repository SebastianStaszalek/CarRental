package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "RENTAL")
public class RentalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @ManyToOne
    private CustomerEntity customer;

    @Column(nullable = false)
    @ManyToOne
    private CarEntity car;

    @Column (name = "start_location", nullable = false)
    @ManyToOne
    private LocationEntity startLocation;

    @Column (name = "end_location")
    @ManyToOne
    private LocationEntity endLocation;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name =  "total_cost")
    private int totalCost;


}
