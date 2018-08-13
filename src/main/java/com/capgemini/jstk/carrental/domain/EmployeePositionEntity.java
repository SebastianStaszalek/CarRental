package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "EMPLOYEE_POSITION")
public class EmployeePositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "position")
    Set<EmployeeEntity> employees;

    public void addEmployee(EmployeeEntity e) {
        e.setPosition(this);
        employees.add(e);
    }


}
