package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.dao.LocationDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.LocationEntity;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import com.capgemini.jstk.carrental.mapper.EmployeeMapper;
import com.capgemini.jstk.carrental.mapper.LocationMapper;
import com.capgemini.jstk.carrental.service.LocationService;
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
    private EmployeeMapper employeeMapper;

    @Autowired
    public LocationServiceImp(LocationMapper locationMapper, LocationDao locationDao,
                              EmployeeMapper employeeMapper, EmployeeDao employeeDao) {
        this.locationMapper = locationMapper;
        this.locationDao = locationDao;
        this.employeeMapper = employeeMapper;
        this.employeeDao = employeeDao;
    }

    @Override
    public LocationTO findLocationById(Long id) {
        return locationMapper.map(locationDao.findOne(id));
    }

    @Override
    public List<LocationTO> getAllLocations() {
        return locationMapper.map2TO(locationDao.findAll());
    }

    @Override
    public LocationTO addLocation(LocationTO location) {
        LocationEntity locationEntity = locationMapper.map(location);
        return locationMapper.map(locationDao.save(locationEntity));
    }

    @Override
    public void deleteLocation(LocationTO location) {
        locationDao.delete(location.getId());
    }

    @Override
    public LocationTO updateLocation(LocationTO location) {
        LocationEntity locationEntity = locationMapper.map(location);
        return locationMapper.map(locationDao.update(locationEntity));
    }

    @Override
    public void addEmployeeToLocation(Long locationId, EmployeeTO employee) {
        EmployeeEntity employeeEntity = employeeDao.findOne(employee.getId());

        LocationEntity location = locationDao.findOne(locationId);
        location.addEmployee(employeeEntity);
    }

    @Override
    public void removeEmployeeFromLocation(Long locationId, EmployeeTO employee) {
        EmployeeEntity employeeEntity = employeeMapper.map(employee);

        LocationEntity location = locationDao.findOne(locationId);
        location.removeEmployee(employeeEntity);
    }
}
