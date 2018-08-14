package com.capgemini.jstk.carrental.dao;

import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.LocationEntity;

import java.util.List;

public interface LocationDao extends Dao<LocationEntity, Long> {

    List<EmployeeEntity> findAllEmployeesInLocation(Long locationId);

    List<EmployeeEntity> findAllCarCarersByCarAndLocation(Long locationId, Long carId);
}
