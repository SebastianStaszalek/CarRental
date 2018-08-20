package com.capgemini.jstk.carrental.service.impl;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.dao.CustomerDao;
import com.capgemini.jstk.carrental.dao.LocationDao;
import com.capgemini.jstk.carrental.dao.RentalDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.domain.CustomerEntity;
import com.capgemini.jstk.carrental.domain.LocationEntity;
import com.capgemini.jstk.carrental.domain.RentalEntity;
import com.capgemini.jstk.carrental.dto.RentalTO;
import com.capgemini.jstk.carrental.exception.message.Message;
import com.capgemini.jstk.carrental.mapper.RentalMapper;
import com.capgemini.jstk.carrental.service.RentalService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RentalServiceImp implements RentalService {

    private RentalMapper rentalMapper;
    private RentalDao rentalDao;

    private CustomerDao customerDao;
    private CarDao carDao;
    private LocationDao locationDao;

    public RentalServiceImp(RentalMapper rentalMapper, RentalDao rentalDao, CustomerDao customerDao,
                            CarDao carDao, LocationDao locationDao) {
        this.rentalMapper = rentalMapper;
        this.rentalDao = rentalDao;
        this.customerDao = customerDao;
        this.carDao = carDao;
        this.locationDao = locationDao;
    }

    @Override
    public RentalTO findRentalById(Long id) {
        Preconditions.checkNotNull(id, Message.EMPTY_ID);

        return rentalMapper.map(rentalDao.findOne(id));
    }

    @Override
    public List<RentalTO> getAllRentals() {
        return rentalMapper.map2TO(rentalDao.findAll());
    }

    @Override
    public RentalTO addRental(RentalTO rentalTO) {
        Preconditions.checkNotNull(rentalTO, Message.EMPTY_OBJECT);

        CustomerEntity customerEntity = customerDao.findOne(rentalTO.getCustomerId());
        CarEntity carEntity = carDao.findOne(rentalTO.getCarId());
        LocationEntity startLocation = locationDao.findOne(rentalTO.getStartLocationId());
        LocationEntity endLocation = locationDao.findOne(rentalTO.getEndLocationId());

        RentalEntity rentalEntity = rentalMapper.map(rentalTO);
        RentalEntity savedRental = rentalDao.save(rentalEntity);

        customerEntity.addRental(savedRental);
        carEntity.addRental(savedRental);
        startLocation.addStartRental(savedRental);
        endLocation.addEndRental(savedRental);

        return rentalMapper.map(rentalDao.findOne(savedRental.getId()));
    }

}
