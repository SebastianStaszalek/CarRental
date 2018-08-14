package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.CarTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;

import java.util.List;

public interface CarService {

    CarTO findCarById(Long id);

    List<CarTO> getAllCars();

    CarTO addCar(CarTO car);

    void deleteCar(CarTO car);

    CarTO updateCar(CarTO car);

    void assignCarToCarer(CarTO car, EmployeeTO employee);

    List<CarTO> findCarByTypeAndBrand(CarTO car);

    List<CarTO> findCarByCarer(EmployeeTO employee);
}
