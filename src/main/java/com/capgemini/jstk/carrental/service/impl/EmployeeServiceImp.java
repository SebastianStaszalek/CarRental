package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.dao.EmployeePositionDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.EmployeePositionEntity;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.mapper.EmployeeMapper;
import com.capgemini.jstk.carrental.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//TODO: przy dodawaniu sprawdz czy podany pracownik lub samochod juz nie istnieje!
//TODO: metoda update nie powinna korzystac z buildera i tworzyc nowy obiekt
@Service
@Transactional
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeMapper employeeMapper;
    private EmployeeDao employeeDao;

    private EmployeePositionDao employeePositionDao;

    @Autowired
    public EmployeeServiceImp(EmployeeMapper employeeMapper, EmployeeDao employeeDao,
                              EmployeePositionDao employeePositionDao) {
        this.employeeMapper = employeeMapper;
        this.employeeDao = employeeDao;
        this.employeePositionDao = employeePositionDao;
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
        EmployeePositionTO employeePositionTO = employee.getEmployeePosition();

        EmployeePositionEntity employeePositionEntity = employeePositionDao.findOne(employeePositionTO.getId());
        EmployeeEntity employeeEntity = employeeMapper.map(employee);

        employeeEntity.setPosition(employeePositionEntity);
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
