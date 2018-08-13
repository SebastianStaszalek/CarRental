package com.capgemini.jstk.carrental.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class AddressEntity implements Serializable {

    @Column(nullable = false, length = 50)
    private String street;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 15)
    private String postalCode;


}
