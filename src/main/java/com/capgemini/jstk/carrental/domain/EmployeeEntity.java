package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @ManyToOne
    private LocationEntity location;

    @ManyToOne
    private EmployeePositionEntity position;

    @ManyToMany (mappedBy = "carers")
    private Collection<CarEntity> cars;
}
