package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.dto.CarTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.exception.CarNotFoundException;
import com.capgemini.jstk.carrental.exception.EmployeeNotFoundException;
import com.capgemini.jstk.carrental.exception.message.Message;
import com.capgemini.jstk.carrental.mapper.CarMapper;
import com.capgemini.jstk.carrental.service.CarService;
import com.google.common.base.Preconditions;
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
        Preconditions.checkNotNull(id, Message.EMPTY_ID);
        return carMapper.map(carDao.findOne(id));
    }

    @Override
    public List<CarTO> getAllCars() {
        return carMapper.map2TO(carDao.findAll());
    }

    @Override
    public CarTO addCar(CarTO car) {
        Preconditions.checkNotNull(car, Message.EMPTY_OBJECT);

        CarEntity carEntity = carMapper.map(car);
        return carMapper.map(carDao.save(carEntity));
    }

    @Override
    public void deleteCar(CarTO car) {
        Preconditions.checkNotNull(car.getId(), Message.EMPTY_ID);

        carDao.delete(car.getId());
    }

    @Override
    public CarTO updateCar(CarTO car) {
        Preconditions.checkNotNull(car.getId(), Message.EMPTY_ID);

        CarEntity carEntity = carMapper.map(car);
        return carMapper.map(carDao.update(carEntity));
    }

    @Override
    public void assignCarToCarer(CarTO car, EmployeeTO employee) {
        Preconditions.checkNotNull(car.getId(), Message.EMPTY_ID);
        Preconditions.checkNotNull(employee.getId(), Message.EMPTY_ID);

        CarEntity carEntity = carDao.findOne(car.getId());
        EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());

        if(carEntity == null) {
            throw new CarNotFoundException(Message.CAR_NOT_FOUND);
        }
        if(employeeEntity == null) {
            throw new EmployeeNotFoundException(Message.EMPLOYEE_NOT_FOUND);
        }

        employeeEntity.addCar(carEntity);
    }

    @Override
    public List<CarTO> findCarByTypeAndBrand(CarTO car) {
        Preconditions.checkNotNull(car.getCarType(), Message.EMPTY_FIELD);
        Preconditions.checkNotNull(car.getBrand(), Message.EMPTY_FIELD);

        String carType = car.getCarType();
        String carBrand = car.getBrand();

        List<CarEntity> searchedCarsList = carDao.findCarByTypeAndBrand(carType, carBrand);

        if(searchedCarsList.isEmpty()) {
            throw new CarNotFoundException(Message.NO_CAR_FOUND);
        }

        return carMapper.map2TO(searchedCarsList);
    }

    @Override
    public List<CarTO> findCarByCarer(EmployeeTO employee) {
        Preconditions.checkNotNull(employee.getId(), Message.EMPTY_ID);

        List<CarEntity> searchedCarsList = carDao.findCarByCarer(employee.getId());

        if(searchedCarsList.isEmpty()) {
            throw new CarNotFoundException(Message.NO_CAR_FOUND);
        }

        return carMapper.map2TO(searchedCarsList);
    }

    @Override
    public List<CarTO> findCarsRentedByDifferentCustomers(Long customersNum) {
        Preconditions.checkNotNull(customersNum, Message.EMPTY_FIELD);

        List<CarEntity> searchedCarsList = carDao.findCarsRentedByDifferentCustomers(customersNum);

        if(searchedCarsList.isEmpty()) {
            throw new CarNotFoundException(Message.NO_CAR_FOUND);
        }

        return carMapper.map2TO(searchedCarsList);
    }

    @Override
    public List<CarTO> findCarsRentedInGivenPeriodOfTime(Date from, Date to) {
        Preconditions.checkNotNull(from, Message.EMPTY_FIELD);
        Preconditions.checkNotNull(to, Message.EMPTY_FIELD);

        List<CarEntity> searchedCarsList = carDao.findCarsRentedInGivenPeriodOfTime(from, to);

        if(searchedCarsList.isEmpty()) {
            throw new CarNotFoundException(Message.NO_CAR_FOUND);
        }

        return carMapper.map2TO(searchedCarsList);
    }
}
