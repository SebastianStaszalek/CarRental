package com.capgemini.jstk.carrental.domain;

import com.capgemini.jstk.carrental.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"employees", "startRentals", "endRentals", "cars"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOCATION")
public class LocationEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Column(name = "phone", nullable = false)
    private int phoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "location")
    Set<EmployeeEntity> employees = new HashSet<>();

    @OneToMany(mappedBy = "startLocation")
    Set<RentalEntity> startRentals;

    @OneToMany(mappedBy = "endLocation")
    Set<RentalEntity> endRentals;

    @OneToMany(mappedBy = "currentLocation")
    Set<CarEntity> cars;


    public void addEmployee(EmployeeEntity employee) {
        this.employees.add(employee);
        employee.setLocation(this);
    }

    public void removeEmployee(EmployeeEntity employee) {
        employees.remove(employee);
        employee.setLocation(null);
    }

    public void addStartRental(RentalEntity rental) {
        rental.setStartLocation(this);
        startRentals.add(rental);
    }

    public void addEndRental(RentalEntity rental) {
        rental.setEndLocation(this);
        endRentals.add(rental);
    }

}
