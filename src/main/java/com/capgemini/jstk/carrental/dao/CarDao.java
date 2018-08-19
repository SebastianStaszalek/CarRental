package com.capgemini.jstk.carrental.dao;

import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.domain.RentalEntity;

import java.util.Date;
import java.util.List;

public interface CarDao extends Dao<CarEntity, Long> {

    List<CarEntity> findCarByTypeAndBrand(String type, String brand);

    List<CarEntity> findCarByCarer(Long id);

    List<CarEntity> findCarsRentedByDifferentCustomers();

    List<CarEntity> findCarsRentedInGivenPeriodOfTime(Date from, Date to);

}
