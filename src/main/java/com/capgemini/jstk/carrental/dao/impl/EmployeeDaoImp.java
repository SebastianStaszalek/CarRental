package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImp extends AbstractDao<EmployeeEntity, Long> implements EmployeeDao {
}
