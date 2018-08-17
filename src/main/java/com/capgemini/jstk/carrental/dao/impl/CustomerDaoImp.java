package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.CustomerDao;
import com.capgemini.jstk.carrental.domain.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImp extends AbstractDao<CustomerEntity, Long> implements CustomerDao {
}
