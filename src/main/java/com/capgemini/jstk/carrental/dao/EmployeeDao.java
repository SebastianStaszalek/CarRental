package com.capgemini.jstk.carrental.dao;

import com.capgemini.jstk.carrental.domain.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends Dao<EmployeeEntity, Long>{

    List<EmployeeEntity> findAllEmployeesInLocation(Long locationId);

    List<EmployeeEntity> findAllCarCarersByCarAndLocation(Long locationId, Long carId);

    List<EmployeeEntity> findEmployeesByPosition(Long positionId);
}
