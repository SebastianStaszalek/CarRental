package com.capgemini.jstk.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTAL")
public class RentalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToOne
    private CarEntity car;

    @ManyToOne
    private LocationEntity startLocation;

    @ManyToOne
    private LocationEntity endLocation;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name =  "total_cost")
    private int totalCost;


}
