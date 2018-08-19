package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.dto.CarTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.mapper.CarMapper;
import com.capgemini.jstk.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CarServiceImp implements CarService {

    private CarMapper carMapper;
    private CarDao carDao;
    private EmployeeDao employeeDao;

    @Autowired
    public CarServiceImp(CarMapper carMapper, CarDao carDao, EmployeeDao employeeDao) {
        this.carMapper = carMapper;
        this.carDao = carDao;
        this.employeeDao = employeeDao;
    }

    @Override
    public CarTO findCarById(Long id) {
        return carMapper.map(carDao.findOne(id));
    }

    @Override
    public List<CarTO> getAllCars() {
        return carMapper.map2TO(carDao.findAll());
    }

    @Override
    public CarTO addCar(CarTO car) {
        CarEntity carEntity = carMapper.map(car);
        return carMapper.map(carDao.save(carEntity));
    }

    @Override
    public void deleteCar(CarTO car) {
        carDao.delete(car.getId());
    }

    @Override
    public CarTO updateCar(CarTO car) {
        CarEntity carEntity = carMapper.map(car);
        return carMapper.map(carDao.update(carEntity));
    }

    @Override
    public void assignCarToCarer(CarTO car, EmployeeTO employee) {

        CarEntity carEntity = carDao.findOne(car.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());

        employeeEntity.addCar(carEntity);
    }

    @Override
    public List<CarTO> findCarByTypeAndBrand(CarTO car) {

        String carType = car.getCarType();
        String carBrand = car.getBrand();

        return carMapper.map2TO(carDao.findCarByTypeAndBrand(carType, carBrand));
    }

    @Override
    public List<CarTO> findCarByCarer(EmployeeTO employee) {
        return carMapper.map2TO(carDao.findCarByCarer(employee.getId()));
    }

    @Override
    public List<CarTO> findCarsRentedByDifferentCustomers() {
        return carMapper.map2TO(carDao.findCarsRentedByDifferentCustomers());
    }

    @Override
    public List<CarTO> findCarsRentedInGivenPeriodOfTime(Date from, Date to) {
        return carMapper.map2TO(carDao.findCarsRentedInGivenPeriodOfTime(from, to));
    }
}
