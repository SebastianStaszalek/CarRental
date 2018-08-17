package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.CustomerTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void shouldAddCustomers() {
        //given
        CustomerTO customer = createFirtsCustomer();
        CustomerTO customer2 = createSecondCustomer();

        CustomerTO savedCustomer = customerService.addCustomer(customer);
        CustomerTO savedCustomer2 = customerService.addCustomer(customer2);

        //when
        CustomerTO customerToCheck = customerService.findCustomerById(savedCustomer.getId());

        //then
        assertThat(customerToCheck.getName()).isEqualTo("Adam");
        assertThat(savedCustomer2.getId()).isNotNull();
    }

    @Test
    public void shouldFindAllCustomers() {
        //given
        CustomerTO customer = createFirtsCustomer();
        CustomerTO customer2 = createSecondCustomer();

        customerService.addCustomer(customer);
        customerService.addCustomer(customer2);

        //when
        List<CustomerTO> customersList = customerService.getAllCustomers();

        //then
        assertThat(customersList.size()).isEqualTo(2);
    }

    private CustomerTO createFirtsCustomer() {
        return CustomerTO.builder()
                .name("Adam")
                .surname("Kowalski")
                .eMail("ad@gmail.com")
                .mobilePhone(600253400)
                .creditCard("4684732941845524")
                .drivingLicense("120/155/17")
                .dateOfBirth(Date.valueOf("1980-11-23"))
                .address(Address.builder()
                        .street("Jackowskiego 30")
                        .postalCode("60-450")
                        .city("Poznan").build())
                .build();
    }

    private CustomerTO createSecondCustomer() {
        return CustomerTO.builder()
                .name("Paulina")
                .surname("Tracz")
                .eMail("paula@gmail.com")
                .mobilePhone(608253400)
                .creditCard("7084732941845524")
                .drivingLicense("300/155/17")
                .dateOfBirth(Date.valueOf("1982-12-10"))
                .address(Address.builder()
                        .street("Polna 20")
                        .postalCode("60-700")
                        .city("Poznan").build())
                .build();
    }
}