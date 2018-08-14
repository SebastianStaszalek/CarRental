package com.capgemini.jstk.carrental.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String surname;

    @Column(name = "email", length = 30)
    private String eMail;

    @Column(name = "mobile_phone", nullable = false)
    private int mobilePhone;

    @Column(name = "credit_card", nullable = false, length = 16)
    private String creditCard;

    @Column(name = "driving_license", nullable = false, length = 20)
    private String drivingLicense;

    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    @Embedded
    private Address address;

    @OneToMany (mappedBy = "customer")
    Set<RentalEntity> rentals;

}

