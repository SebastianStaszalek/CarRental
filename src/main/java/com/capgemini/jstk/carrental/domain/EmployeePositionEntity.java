package com.capgemini.jstk.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE_POSITION")
public class EmployeePositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "position")
    Set<EmployeeEntity> employees;

    public void addEmployee(EmployeeEntity employee) {
        employee.setPosition(this);
        employees.add(employee);
    }

    public void deleteEmployee(EmployeeEntity employee) {
        employee.getPosition().
        employees.remove(employee);
    }


}
