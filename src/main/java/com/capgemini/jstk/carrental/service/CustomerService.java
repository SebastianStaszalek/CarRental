package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.CustomerTO;

import java.util.List;

public interface CustomerService {

    CustomerTO findCustomerById(Long id);

    List<CustomerTO> getAllCustomers();

    CustomerTO addCustomer(CustomerTO customer);
}
