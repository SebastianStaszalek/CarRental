package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.EmployeePositionDao;
import com.capgemini.jstk.carrental.domain.EmployeePositionEntity;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.exception.message.Message;
import com.capgemini.jstk.carrental.mapper.EmployeePositionMapper;
import com.capgemini.jstk.carrental.service.EmployeePositionService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeePositionServiceImp implements EmployeePositionService {

    private EmployeePositionDao employeePositionDao;
    private EmployeePositionMapper employeePositionMapper;

    @Autowired
    public EmployeePositionServiceImp(EmployeePositionDao employeePositionDao, EmployeePositionMapper employeePositionMapper) {
        this.employeePositionDao = employeePositionDao;
        this.employeePositionMapper = employeePositionMapper;
    }

    @Override
    public EmployeePositionTO addEmployeePosition(EmployeePositionTO employeePosition) {
        Preconditions.checkNotNull(employeePosition, Message.EMPTY_OBJECT);

        EmployeePositionEntity employeePositionEntity = employeePositionMapper.map(employeePosition);
        return employeePositionMapper.map(employeePositionDao.save(employeePositionEntity));
    }
}
