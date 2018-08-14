package com.capgemini.jstk.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<CarEntity> cars;

    public void addCar(CarEntity car) {
        cars.add(car);
        car.addCarer(this);
    }

    public void removeCar(CarEntity car) {
        cars.remove(car);
    }

   }
