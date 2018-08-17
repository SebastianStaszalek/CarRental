package com.capgemini.jstk.carrental.dto;

import com.capgemini.jstk.carrental.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTO {

    private Long id;
    private String name;
    private String surname;
    private String eMail;
    private int mobilePhone;
    private String creditCard;
    private String drivingLicense;
    private Date dateOfBirth;
    private Address address;
}
