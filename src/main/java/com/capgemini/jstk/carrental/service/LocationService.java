package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;

import java.util.List;

public interface LocationService {

    LocationTO findLocationById(Long id);

    List<LocationTO> getAllLocations();

    LocationTO addLocation(LocationTO location);

    void deleteLocation(LocationTO location);

    LocationTO updateLocation(LocationTO location);

    void addEmployeeToLocation(Long locationId, EmployeeTO employee);

    void removeEmployeeFromLocation(Long locationId, EmployeeTO employee);

}
