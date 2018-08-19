package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.RentalTO;

import java.util.List;

public interface RentalService {

    RentalTO findRentalById(Long id);

    List<RentalTO> getAllRentals();

    RentalTO addRental(RentalTO rental);


}
