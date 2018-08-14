package com.capgemini.jstk.carrental.dto;

import com.capgemini.jstk.carrental.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTO {

    private Long id;
    private int phoneNumber;
    private Address address;
}
