package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.mapper.EmployeeMapper;
import com.capgemini.jstk.carrental.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeMapper employeeMapper;
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImp(EmployeeMapper employeeMapper, EmployeeDao employeeDao) {
        this.employeeMapper = employeeMapper;
        this.employeeDao = employeeDao;
    }

    @Override
    public EmployeeTO findEmployeeById(Long id) {
        return employeeMapper.map(employeeDao.findOne(id));
    }

    @Override
    public List<EmployeeTO> getAllEmployees() {
        return employeeMapper.map2TO(employeeDao.findAll());
    }

    @Override
    public EmployeeTO addEmployee(EmployeeTO employee) {
        EmployeeEntity employeeEntity = employeeMapper.map(employee);
        return employeeMapper.map(employeeDao.save(employeeEntity));
    }

    @Override
    public void deleteEmployee(EmployeeTO employee) {
        employeeDao.delete(employee.getId());
    }

    @Override
    public List<EmployeeTO> findAllEmployeesByLocation(Long locationId) {
        List<EmployeeEntity> employeeList = employeeDao.findAllEmployeesInLocation(locationId);
        return employeeMapper.map2TO(employeeList);
    }

    @Override
    public List<EmployeeTO> findAllCarCarersByCarAndLocation(Long locationId, Long carId) {
        List<EmployeeEntity> employeeList = employeeDao.findAllCarCarersByCarAndLocation(locationId, carId);
        return employeeMapper.map2TO(employeeList);
    }
}
