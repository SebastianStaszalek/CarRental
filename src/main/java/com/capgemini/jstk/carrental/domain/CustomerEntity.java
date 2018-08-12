package com.capgemini.jstk.carrental.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    @Column(name = "email")
    private String eMail;
    @Column(name = "mobile_phone")
    private int mobilePhone;
    @Column(name = "credit_card")
    private int creditCard;
    @Column(name = "driving_license")
    private String drivingLicense;
    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    private AddressEntity address;

}

