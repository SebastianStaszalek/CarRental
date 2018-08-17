package com.capgemini.jstk.carrental.dao.impl;

import com.capgemini.jstk.carrental.dao.RentalDao;
import com.capgemini.jstk.carrental.domain.RentalEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RentalDaoImp extends AbstractDao<RentalEntity, Long> implements RentalDao {
}
