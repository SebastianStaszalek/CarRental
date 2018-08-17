package com.capgemini.jstk.carrental.domain;

import com.capgemini.jstk.carrental.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(exclude = {"customer", "car", "startLocation", "endLocation"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTAL")
public class RentalEntity extends Auditable<String> implements Serializable {

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
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name =  "total_cost")
    private int totalCost;


}
