package com.capgemini.jstk.carrental.domain;

import com.capgemini.jstk.carrental.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE_POSITION")
public class EmployeePositionEntity extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "position")
    Set<EmployeeEntity> employees;


}
