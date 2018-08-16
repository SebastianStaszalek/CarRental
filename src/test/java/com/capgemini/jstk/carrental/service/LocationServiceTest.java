package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(properties = "spring.profiles.active=mysql")

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

    @Test
    public void shouldFindLocationById() {
        //given
        LocationTO location = createFirstLocation();

        LocationTO savedLocation = locationService.addLocation(location);

        //when
        LocationTO locationToCheck = locationService.findLocationById(savedLocation.getId());

        //then
        assertThat(locationToCheck.getId()).isNotNull();
        assertThat(locationToCheck.getPhoneNumber()).isEqualTo(604567880);
        assertThat(locationToCheck.getAddress().getCity()).isEqualTo("Poznan");
    }

    @Test
    public void shouldDeleteLocation() {
        //given
        LocationTO location1 = createFirstLocation();
        LocationTO location2 = createSecondLocation();
        LocationTO location3 = createThirdLocation();

        LocationTO locationToCheck = locationService.addLocation(location1);
        locationService.addLocation(location2);
        locationService.addLocation(location3);

        //when
        locationService.deleteLocation(locationToCheck);
        List<LocationTO> locationsList = locationService.getAllLocations();

        //then
        assertThat(locationService.findLocationById(locationToCheck.getId())).isNull();
        assertThat(locationsList.size()).isEqualTo(2);
    }

    @Test
    public void shouldUpdateLocation() {
        //given
        LocationTO location1 = createFirstLocation();
        LocationTO location2 = createSecondLocation();

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
        EmployeePositionTO position = createFirstPosition();
        EmployeeTO employee1 = createFirstEmployee();
        EmployeeTO employee2 = createSecondEmployee();
        EmployeeTO employee3 = createThirdEmployee();

        LocationTO location = createFirstLocation();

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



    private LocationTO createFirstLocation() {
        return LocationTO.builder()
                .phoneNumber(604567880)
                .address(Address.builder()
                        .city("Poznan")
                        .street("Wronska 17")
                        .postalCode("60-754").build())
                .build();
    }

    private LocationTO createSecondLocation() {
        return LocationTO.builder()
                .phoneNumber(700567880)
                .address(Address.builder()
                        .city("Gdansk")
                        .street("Rzemieslnicza 5")
                        .postalCode("46-200").build())
                .build();
    }

    private LocationTO createThirdLocation() {
        return LocationTO.builder()
                .phoneNumber(600200100)
                .address(Address.builder()
                        .city("Wroclaw")
                        .street("Polna 10")
                        .postalCode("64-120").build())
                .build();
    }

    private EmployeePositionTO createFirstPosition() {
        return EmployeePositionTO.builder()
                .name("Sprzedawca")
                .build();
    }

    private EmployeeTO createFirstEmployee() {
        return EmployeeTO.builder()
                .name("Tomasz")
                .surname("Kot")
                .employeePosition(EmployeePositionTO.builder()
                        .id(1L)
                        .name("Sprzedawca").build())
                .build();
    }

    private EmployeeTO createSecondEmployee() {
        return EmployeeTO.builder()
                .name("Lukasz")
                .surname("Majek")
                .employeePosition(EmployeePositionTO.builder()
                        .id(1L)
                        .name("Sprzedawca").build())
                .build();
    }

    private EmployeeTO createThirdEmployee() {
        return EmployeeTO.builder()
                .name("Maja")
                .surname("Heller")
                .employeePosition(EmployeePositionTO.builder()
                        .id(1L)
                        .name("Sprzedawca").build())
                .build();
    }
}