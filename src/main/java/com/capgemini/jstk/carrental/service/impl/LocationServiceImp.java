package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.dao.LocationDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.LocationEntity;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import com.capgemini.jstk.carrental.exception.EmployeeNotFoundException;
import com.capgemini.jstk.carrental.exception.LocationNotFoundException;
import com.capgemini.jstk.carrental.exception.message.Message;
import com.capgemini.jstk.carrental.mapper.LocationMapper;
import com.capgemini.jstk.carrental.service.LocationService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationServiceImp implements LocationService {

    private LocationMapper locationMapper;
    private LocationDao locationDao;

    private EmployeeDao employeeDao;

    @Autowired
    public LocationServiceImp(LocationMapper locationMapper, LocationDao locationDao,
                              EmployeeDao employeeDao) {
        this.locationMapper = locationMapper;
        this.locationDao = locationDao;
        this.employeeDao = employeeDao;
    }

    @Override
    public LocationTO findLocationById(Long id) {
        Preconditions.checkNotNull(id, Message.EMPTY_ID);

        LocationEntity searchedLocation = locationDao.findOne(id);

        if(searchedLocation == null) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND);
        }

        return locationMapper.map(searchedLocation);
    }

    @Override
    public List<LocationTO> getAllLocations() {
        return locationMapper.map2TO(locationDao.findAll());
    }

    @Override
    public LocationTO addLocation(LocationTO location) {
        Preconditions.checkNotNull(location, Message.EMPTY_OBJECT);

        LocationEntity locationEntity = locationMapper.map(location);
        return locationMapper.map(locationDao.save(locationEntity));
    }

    @Override
    public void deleteLocation(LocationTO location) {
        Preconditions.checkNotNull(location, Message.EMPTY_ID);

        locationDao.delete(location.getId());
    }

    @Override
    public LocationTO updateLocation(LocationTO location) {
        Preconditions.checkNotNull(location, Message.EMPTY_OBJECT);

        LocationEntity locationEntity = locationMapper.map(location);
        return locationMapper.map(locationDao.update(locationEntity));
    }

    @Override
    public void addEmployeeToLocation(Long locationId, EmployeeTO employee) {
        Preconditions.checkNotNull(locationId, Message.EMPTY_ID);
        Preconditions.checkNotNull(employee.getId(), Message.EMPTY_ID);

        EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());

        LocationEntity location = locationDao.findOne(locationId);

        if(location == null) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND);
        }
        if(employeeEntity == null) {
            throw new EmployeeNotFoundException(Message.EMPLOYEE_NOT_FOUND);
        }

        location.addEmployee(employeeEntity);
    }

    @Override
    public void removeEmployeeFromLocation(Long locationId, EmployeeTO employee) {
        Preconditions.checkNotNull(locationId, Message.EMPTY_ID);
        Preconditions.checkNotNull(employee.getId(), Message.EMPTY_ID);

        EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());

        LocationEntity location = locationDao.findOne(locationId);

        if(location == null) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND);
        }
        if(employeeEntity == null) {
            throw new EmployeeNotFoundException(Message.EMPLOYEE_NOT_FOUND);
        }

        location.removeEmployee(employeeEntity);
    }
}
