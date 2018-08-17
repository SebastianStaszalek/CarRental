package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.CustomerDao;
import com.capgemini.jstk.carrental.domain.CustomerEntity;
import com.capgemini.jstk.carrental.dto.CustomerTO;
import com.capgemini.jstk.carrental.mapper.CustomerMapper;
import com.capgemini.jstk.carrental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImp implements CustomerService {

    private CustomerMapper customerMapper;
    private CustomerDao customerDao;

    @Autowired
    public CustomerServiceImp(CustomerMapper customerMapper, CustomerDao customerDao) {
        this.customerMapper = customerMapper;
        this.customerDao = customerDao;
    }

    @Override
    public CustomerTO findCustomerById(Long id) {
        return customerMapper.map(customerDao.findOne(id));
    }

    @Override
    public List<CustomerTO> getAllCustomers() {
        return customerMapper.map2TO(customerDao.findAll());
    }

    @Override
    public CustomerTO addCustomer(CustomerTO customer) {
        CustomerEntity customerEntity = customerMapper.map(customer);
        return customerMapper.map(customerDao.save(customerEntity));
    }
}
