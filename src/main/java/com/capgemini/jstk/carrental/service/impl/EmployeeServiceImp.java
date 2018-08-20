package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.dao.EmployeePositionDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.domain.EmployeePositionEntity;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.exception.EmployeeNotFoundException;
import com.capgemini.jstk.carrental.exception.message.Message;
import com.capgemini.jstk.carrental.mapper.EmployeeMapper;
import com.capgemini.jstk.carrental.service.EmployeeService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Preconditions.checkNotNull(id, Message.EMPTY_ID);

        EmployeeEntity searchedEmployee = employeeDao.findOne(id);

        if(searchedEmployee == null) {
            throw new EmployeeNotFoundException(Message.EMPLOYEE_NOT_FOUND);
        }

        return employeeMapper.map(searchedEmployee);
    }

    @Override
    public EmployeeTO addEmployee(EmployeeTO employee) {
        Preconditions.checkNotNull(employee, Message.EMPTY_OBJECT);

        EmployeePositionTO employeePositionTO = employee.getEmployeePosition();

        EmployeePositionEntity employeePositionEntity = employeePositionDao.findOne(employeePositionTO.getId());
        EmployeeEntity employeeEntity = employeeMapper.map(employee);

        employeeEntity.setPosition(employeePositionEntity);
        return employeeMapper.map(employeeDao.save(employeeEntity));
    }

    @Override
    public void deleteEmployee(EmployeeTO employee) {
        Preconditions.checkNotNull(employee.getId(), Message.EMPTY_ID);

        employeeDao.delete(employee.getId());
    }

    @Override
    public List<EmployeeTO> findAllEmployeesByLocation(Long locationId) {
        Preconditions.checkNotNull(locationId, Message.EMPTY_FIELD);

        List<EmployeeEntity> employeeList = employeeDao.findAllEmployeesInLocation(locationId);

        if(employeeList.isEmpty()) {
            throw new EmployeeNotFoundException(Message.NO_EMPLOYEE_FOUND);
        }

        return employeeMapper.map2TO(employeeList);
    }

    @Override
    public List<EmployeeTO> findAllCarCarersByCarAndLocation(Long locationId, Long carId) {
        Preconditions.checkNotNull(locationId, Message.EMPTY_FIELD);
        Preconditions.checkNotNull(carId, Message.EMPTY_FIELD);

        List<EmployeeEntity> employeeList = employeeDao.findAllCarCarersByCarAndLocation(locationId, carId);

        if(employeeList.isEmpty()) {
            throw new EmployeeNotFoundException(Message.NO_EMPLOYEE_FOUND);
        }

        return employeeMapper.map2TO(employeeList);
    }

    @Override
    public List<EmployeeTO> findEmployeesByPosition(Long positionId) {
        Preconditions.checkNotNull(positionId, Message.EMPTY_FIELD);

        List<EmployeeEntity> employeeList = employeeDao.findEmployeesByPosition(positionId);

        if(employeeList.isEmpty()) {
            throw new EmployeeNotFoundException(Message.NO_EMPLOYEE_FOUND);
        }

        return employeeMapper.map2TO(employeeList);
    }
}
