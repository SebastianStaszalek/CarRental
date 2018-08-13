package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.domain.CarEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class CarDaoImp extends AbstractDao<CarEntity, Long> implements CarDao {

    @Override
    public List<CarEntity> findCarByTypeAndBrand(String type, String brand) {

        TypedQuery<CarEntity> query = entityManager.createQuery(
                "select car from CarEntity car where upper(car.carType) like concat(upper(:carType), '%') " +
                        "and upper(car.brand) like concat(upper(:brand), '%') ", CarEntity.class
        );

        query.setParameter("carType", type);
        query.setParameter("brand", brand);

        return query.getResultList();
    }
}
