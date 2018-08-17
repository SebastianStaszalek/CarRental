package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.LocationDao;
import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.domain.LocationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDaoImp extends AbstractDao<LocationEntity, Long> implements LocationDao {

    @Override
    public LocationEntity update(LocationEntity entity) {
        int phoneNumber = entity.getPhoneNumber();
        Address address = entity.getAddress();

        LocationEntity locationToUpdate = findOne(entity.getId());
        locationToUpdate.setPhoneNumber(phoneNumber);
        locationToUpdate.setAddress(address);

        return locationToUpdate;
    }
}
