package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.CustomerTO;
import com.capgemini.jstk.carrental.exception.CustomerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    TestTO testTO;

    @Test
    public void shouldAddCustomer() {
        //given
        CustomerTO customer = testTO.createFirstCustomer();

        //when
        CustomerTO savedCustomer = customerService.addCustomer(customer);

        //then
        assertThat(savedCustomer.getId()).isNotNull();
    }

    @Test
    public void shouldFindCustomerById() {
        //given
        CustomerTO customer = testTO.createFirstCustomer();
        CustomerTO customer2 = testTO.createSecondCustomer();

        CustomerTO savedCustomer = customerService.addCustomer(customer);
        CustomerTO savedCustomer2 = customerService.addCustomer(customer2);

        //when
        CustomerTO customerToCheck = customerService.findCustomerById(savedCustomer.getId());

        //then
        assertThat(customerToCheck.getName()).isEqualTo("Adam");
        assertThat(savedCustomer2.getId()).isNotNull();
    }

    @Test(expected = CustomerNotFoundException.class)
    public void shouldThrowCustomerNotFoundException() {
        //given
        CustomerTO customer = testTO.createFirstCustomer();
        customerService.addCustomer(customer);

        //when
        customerService.findCustomerById(4L);
    }

    @Test
    public void shouldFindAllCustomers() {
        //given
        CustomerTO customer = testTO.createFirstCustomer();
        CustomerTO customer2 = testTO.createSecondCustomer();

        customerService.addCustomer(customer);
        customerService.addCustomer(customer2);

        //when
        List<CustomerTO> customersList = customerService.getAllCustomers();

        //then
        assertThat(customersList.size()).isEqualTo(2);
    }

}