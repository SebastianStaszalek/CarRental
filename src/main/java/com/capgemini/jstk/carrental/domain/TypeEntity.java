package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TYPE")
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
