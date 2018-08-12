package com.capgemini.jstk.carrental.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "RENTAL")
public class RentalEntity implements Serializable {
}
