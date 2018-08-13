package com.capgemini.jstk.carrental.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Embeddable
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 15)
    private String postalCode;


}
