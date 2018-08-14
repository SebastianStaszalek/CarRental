package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeTO map(EmployeeEntity employeeEntity) {
        if (employeeEntity != null) {
            return EmployeeTO.builder()
                    .id(employeeEntity.getId())
                    .name(employeeEntity.getName())
                    .surname(employeeEntity.getSurname())
                    .build();
        }
        return null;
    }

    public EmployeeEntity map(EmployeeTO employeeTO) {
        if (employeeTO != null) {
            return EmployeeEntity.builder()
                    .id(employeeTO.getId())
                    .name(employeeTO.getName())
                    .surname(employeeTO.getSurname())
                    .build();
        }
        return null;
    }

    public List<EmployeeTO> map2TO(List<EmployeeEntity> employeeEntities) {
        return employeeEntities.stream().map(this::map).collect(Collectors.toList());
    }

    public List<EmployeeEntity> map2Entity(List<EmployeeTO> employeeTOs) {
        return employeeTOs.stream().map(this::map).collect(Collectors.toList());
    }

}
