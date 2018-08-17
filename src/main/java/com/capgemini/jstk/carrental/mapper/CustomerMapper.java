package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.CustomerEntity;
import com.capgemini.jstk.carrental.dto.CustomerTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerTO map(CustomerEntity customerEntity) {
        if (customerEntity != null) {
            return CustomerTO.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .surname(customerEntity.getSurname())
                    .eMail(customerEntity.getEMail())
                    .mobilePhone(customerEntity.getMobilePhone())
                    .creditCard(customerEntity.getCreditCard())
                    .drivingLicense(customerEntity.getDrivingLicense())
                    .dateOfBirth(customerEntity.getDateOfBirth())
                    .address(customerEntity.getAddress())
                    .build();
        }
        return null;
    }

    public CustomerEntity map(CustomerTO customerTO) {
        if (customerTO != null) {
            return CustomerEntity.builder()
                    .id(customerTO.getId())
                    .name(customerTO.getName())
                    .surname(customerTO.getSurname())
                    .eMail(customerTO.getEMail())
                    .mobilePhone(customerTO.getMobilePhone())
                    .creditCard(customerTO.getCreditCard())
                    .drivingLicense(customerTO.getDrivingLicense())
                    .dateOfBirth(customerTO.getDateOfBirth())
                    .address(customerTO.getAddress())
                    .build();
        }
        return null;
    }

    public List<CustomerTO> map2TO(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(this::map).collect(Collectors.toList());
    }

}
