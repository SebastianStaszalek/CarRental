package com.capgemini.jstk.carrental.dao;

import com.capgemini.jstk.carrental.domain.CarEntity;

import java.util.List;

public interface CarDao extends Dao<CarEntity, Long> {

    List<CarEntity> findCarByTypeAndBrand(String type, String brand);



}
