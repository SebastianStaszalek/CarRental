package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.LocationDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.LocationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDaoImp extends AbstractDao<LocationEntity, Long> implements LocationDao {

    @Override
    public List<EmployeeEntity> findAllEmployeesInLocation(Long locationId) {
        return null;
    }

    @Override
    public List<EmployeeEntity> findAllCarCarersByCarAndLocation(Long locationId, Long carId) {
        return null;
    }
}
