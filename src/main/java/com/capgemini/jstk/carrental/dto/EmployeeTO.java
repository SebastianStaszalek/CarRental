package com.capgemini.jstk.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTO {

    private Long id;
    private String name;
    private String surname;
    private EmployeePositionTO employeePosition;
}
