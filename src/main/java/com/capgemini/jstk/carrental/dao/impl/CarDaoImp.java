package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
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

    @Override
    public List<CarEntity> findCarByCarer(Long id) {

        TypedQuery<CarEntity> query = entityManager.createQuery(
          "select c from CarEntity c inner join c.carers cc" +
                  "where cc.id = :id", CarEntity.class
        );

        query.setParameter("employeeId", id);

        return query.getResultList();
    }


}
