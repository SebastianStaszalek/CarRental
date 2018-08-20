package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import com.capgemini.jstk.carrental.exception.LocationNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @SpringBootTest(properties = "spring.profiles.active=mysql")
 * configuration for running tests on mysql db
 */
//

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeePositionService employeePositionService;

    @Autowired
    TestTO testTO;

    @Test
    public void shouldAddLocation() {
        //given
        LocationTO location = testTO.createFirstLocation();

        //when
        LocationTO savedLocation = locationService.addLocation(location);

        //then
        assertThat(savedLocation.getId()).isNotNull();
        assertThat(location.getPhoneNumber()).isEqualTo(savedLocation.getPhoneNumber());
    }

    @Test
    public void shouldFindLocationById() {
        //given
        LocationTO location = testTO.createFirstLocation();

        LocationTO savedLocation = locationService.addLocation(location);

        //when
        LocationTO locationToCheck = locationService.findLocationById(savedLocation.getId());

        //then
        assertThat(locationToCheck.getId()).isNotNull();
        assertThat(locationToCheck.getPhoneNumber()).isEqualTo(604567880);
        assertThat(locationToCheck.getAddress().getCity()).isEqualTo("Poznan");
    }

    @Test(expected = LocationNotFoundException.class)
    public void shouldThrowLocationNotFoundException() {
        //given
        LocationTO location = testTO.createFirstLocation();

        locationService.addLocation(location);

        //when
       locationService.findLocationById(3L);
    }

    @Test
    public void shouldGetAllLocations() {
        //given
        LocationTO location1 = testTO.createFirstLocation();
        LocationTO location2 = testTO.createSecondLocation();

        locationService.addLocation(location1);
        locationService.addLocation(location2);

        //when
        List<LocationTO> locationsList = locationService.getAllLocations();

        assertThat(locationsList).isNotEmpty();
        assertThat(locationsList.size()).isEqualTo(2);
    }

    @Test
    public void shouldDeleteLocation() {
        //given
        LocationTO location1 = testTO.createFirstLocation();
        LocationTO location2 = testTO.createSecondLocation();
        LocationTO location3 = testTO.createThirdLocation();

        LocationTO locationToCheck = locationService.addLocation(location1);
        locationService.addLocation(location2);
        locationService.addLocation(location3);

        //when
        locationService.deleteLocation(locationToCheck);
        List<LocationTO> locationsList = locationService.getAllLocations();

        //then
        assertThat(locationsList.size()).isEqualTo(2);
    }

    @Test
    public void shouldUpdateLocation() {
        //given
        LocationTO location1 = testTO.createFirstLocation();
        LocationTO location2 = testTO.createSecondLocation();

        locationService.addLocation(location1);
        LocationTO savedLocation = locationService.addLocation(location2);

        //when
        Address newAddress = Address.builder()
                .city("Gdansk")
                .street("Wodna 5")
                .postalCode("46-250")
                .build();

        savedLocation.setPhoneNumber(600100200);
        savedLocation.setAddress(newAddress);

        locationService.updateLocation(savedLocation);

        LocationTO updatedLocation = locationService.findLocationById(savedLocation.getId());

        //then
        assertThat(updatedLocation.getPhoneNumber()).isEqualTo(600100200);
        assertThat(updatedLocation.getAddress().getStreet()).isEqualTo("Wodna 5");
    }

    @Test
    public void shouldRemoveEmployeeFromLocation() {
        //given
        EmployeePositionTO position = testTO.createFirstPosition();
        EmployeeTO employee1 = testTO.createFirstEmployee();
        EmployeeTO employee2 = testTO.createSecondEmployee();
        EmployeeTO employee3 = testTO.createSecondEmployee();

        LocationTO location = testTO.createFirstLocation();

        employeePositionService.addEmployeePosition(position);
        EmployeeTO savedEmployee1 = employeeService.addEmployee(employee1);
        EmployeeTO savedEmployee2 = employeeService.addEmployee(employee2);
        EmployeeTO savedEmployee3 = employeeService.addEmployee(employee3);
        LocationTO savedLocation = locationService.addLocation(location);

        //when
        locationService.addEmployeeToLocation(savedLocation.getId(), savedEmployee1);
        locationService.addEmployeeToLocation(savedLocation.getId(), savedEmployee2);
        locationService.addEmployeeToLocation(savedLocation.getId(), savedEmployee3);

        locationService.removeEmployeeFromLocation(savedLocation.getId(), savedEmployee1);
        locationService.removeEmployeeFromLocation(savedLocation.getId(), savedEmployee3);

        List<EmployeeTO> employeesList = employeeService.findAllEmployeesByLocation(savedLocation.getId());

        EmployeeTO EmpFromLocation = employeesList.get(0);

        EmployeeTO removedEmployee = employeeService.findEmployeeById(savedEmployee1.getId());
        //then
        assertThat(employeesList.size()).isEqualTo(1);
        assertThat(removedEmployee.getId()).isNotNull();
        assertThat(EmpFromLocation.getSurname()).isEqualTo("Majek");
    }

}