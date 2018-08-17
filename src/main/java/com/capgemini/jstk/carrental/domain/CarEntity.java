package com.capgemini.jstk.carrental.domain;

import com.capgemini.jstk.carrental.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CAR")
public class CarEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, length = 20)
    private String brand;

    @Column(nullable = false, length = 20)
    private String model;

    //@ManyToOne
    @Column(name = "car_type", nullable = false, length = 20)
    private String carType;

    @Column(name = "production_year", nullable = false)
    private int productionYear;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(name = "engine_capacity", nullable = false)
    private int engineCapacity;

    @Column(nullable = false)
    private int power;

    @Column(nullable = false)
    private int mileage;

    @ManyToOne
    private LocationEntity currentLocation;

    @OneToMany(mappedBy = "car")
    Set<RentalEntity> rentals;

    @ManyToMany (mappedBy = "cars")
    private Set<EmployeeEntity> carers = new HashSet<>();


    private Set<EmployeeEntity> getAllOwners() {
        return carers;
    }

    public void addOwner(EmployeeEntity newOwner) {
        getAllOwners().add(newOwner);
        newOwner.updateCarReference(this);
    }

    protected void updateOwnerReference(EmployeeEntity employeeEntity) {
        carers.add(employeeEntity);
    }

    public void addRental(RentalEntity rental) {
        rental.setCar(this);
        rentals.add(rental);
    }

    public void removeRental(RentalEntity rental) {
        rentals.remove(rental);
    }

}
