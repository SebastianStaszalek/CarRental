package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.EmployeeDao;
import com.capgemini.jstk.carrental.domain.EmployeeEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EmployeeDaoImp extends AbstractDao<EmployeeEntity, Long> implements EmployeeDao {

    @Override
    public List<EmployeeEntity> findAllEmployeesInLocation(Long locationId) {
        TypedQuery<EmployeeEntity> query = entityManager.createQuery(
                "select emp from EmployeeEntity emp inner join emp.location l " +
                        "where l.id = :id", EmployeeEntity.class
        );

        query.setParameter("id", locationId);

        return query.getResultList();
    }

    @Override
    public List<EmployeeEntity> findAllCarCarersByCarAndLocation(Long locationId, Long carId) {
        TypedQuery<EmployeeEntity> query = entityManager.createQuery(
                "select emp from EmployeeEntity emp join emp.cars c " +
                        "where c.id = :carId and emp.location.id = :locationId" , EmployeeEntity.class
        );

        query.setParameter("locationId", locationId);
        query.setParameter("carId", carId);

        return query.getResultList();
    }
}
