package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.EmployeeTO;

import java.util.List;

public interface EmployeeService {

    EmployeeTO findEmployeeById(Long id);

    List<EmployeeTO> getAllEmployees();

    EmployeeTO addEmployee(EmployeeTO employee);

    void deleteEmployee(EmployeeTO employee);

    List<EmployeeTO> findAllEmployeesByLocation(Long locationId);

    List<EmployeeTO> findAllCarCarersByCarAndLocation(Long locationId, Long carId);

}
