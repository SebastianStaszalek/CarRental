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
    Set<EmployeeEntity> employees;

    @OneToMany(mappedBy = "startLocation")
    Set<RentalEntity> startRentals;

    @OneToMany(mappedBy = "endLocation")
    Set<RentalEntity> endRentals;

    @OneToMany(mappedBy = "currentLocation")
    Set<CarEntity> cars;

    public void addEmployee(EmployeeEntity employee) {
        employee.setLocation(this);
        employees.add(employee);
    }

    public void removeEmployee(EmployeeEntity employee) {
        employees.remove(employee);
    }

    public void addStartRental(RentalEntity rental) {
        rental.setStartLocation(this);
        startRentals.add(rental);
    }

    public void removeStartRental(RentalEntity rental) {
        startRentals.remove(rental);
    }

    public void addEndRental(RentalEntity rental) {
        rental.setEndLocation(this);
        endRentals.add(rental);
    }

    public void removeEndRental(RentalEntity rental) {
        endRentals.remove(rental);
    }

    public void addCar(CarEntity car) {
        car.setCurrentLocation(this);
        cars.add(car);
    }

    public void removeCar(CarEntity car) {
        cars.remove(car);
    }
}
