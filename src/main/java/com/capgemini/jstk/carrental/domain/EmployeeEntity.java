package com.capgemini.jstk.carrental.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"location", "position", "cars"})
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


    @ManyToMany
    @JoinTable(name = "CAR_CARER",
            joinColumns = {@JoinColumn(name = "EMPLOYEE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CAR_ID")}
    )
    private Set<CarEntity> cars = new HashSet<>();


    private Set<CarEntity> getAllCars() {
        return cars;
    }

    public void addCar(CarEntity newCar) {
        getAllCars().add(newCar);
        newCar.updateOwnerReference(this);
    }

    protected void updateCarReference(CarEntity carEntity) {
        cars.add(carEntity);
    }

    public void removeCar(CarEntity car) {
        cars.remove(car);
    }



   }
