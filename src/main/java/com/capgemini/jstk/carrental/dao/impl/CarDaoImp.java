package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class CarDaoImp extends AbstractDao<CarEntity, Long> implements CarDao {

    @Override
    public CarEntity update(CarEntity entity) {
        String brand = entity.getBrand();
        String model = entity.getModel();
        String carType = entity.getCarType();
        int productionYear = entity.getProductionYear();
        String color = entity.getColor();
        int engineCapacity = entity.getEngineCapacity();
        int power = entity.getPower();
        int mileage = entity.getMileage();

        CarEntity entityToUpdate = findOne(entity.getId());
        entityToUpdate.setBrand(brand);
        entityToUpdate.setModel(model);
        entityToUpdate.setCarType(carType);
        entityToUpdate.setProductionYear(productionYear);
        entityToUpdate.setColor(color);
        entityToUpdate.setEngineCapacity(engineCapacity);
        entityToUpdate.setPower(power);
        entityToUpdate.setMileage(mileage);

        return entityToUpdate;
    }

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
          "select car from CarEntity car inner join car.carers cc " +
                  "where cc.id = :id", CarEntity.class
        );

        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public List<CarEntity> findCarsRentedByDifferentCustomers(Long customersNum) {

        TypedQuery<CarEntity> query = entityManager.createQuery(
        "select car from CarEntity car " +
                "where car.id in (select re.car.id from RentalEntity re " +
                "group by re.car.id " +
                "having count(distinct re.customer.id) > :customersNum)", CarEntity.class
        );

        query.setParameter("customersNum", customersNum);

        return query.getResultList();
    }

    @Override
    public List<CarEntity> findCarsRentedInGivenPeriodOfTime(Date fromDate, Date toDate) {

        TypedQuery<CarEntity> query = entityManager.createQuery(
          "select car from CarEntity car join car.rentals r " +
                  "where r.startDate <= :fromDate and r.endDate >= :toDate", CarEntity.class
        );

        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        return query.getResultList();
    }


}
