package com.capgemini.jstk.carrental.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class EmployeeTO {

    private Long id;
    private String name;
    private String surname;
}
