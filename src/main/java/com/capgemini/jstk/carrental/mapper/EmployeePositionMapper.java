package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.EmployeePositionEntity;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeePositionMapper {

    public EmployeePositionTO map(EmployeePositionEntity employeePositionEntity) {
        if (employeePositionEntity != null) {
            return EmployeePositionTO.builder()
                    .id(employeePositionEntity.getId())
                    .name(employeePositionEntity.getName())
                    .build();
        }
        return null;
    }

    public EmployeePositionEntity map(EmployeePositionTO employeePositionTO) {
        if (employeePositionTO != null) {
            return EmployeePositionEntity.builder()
                    .id(employeePositionTO.getId())
                    .name(employeePositionTO.getName())
                    .build();
        }
        return null;
    }
}
