package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.CarTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeServiceTest {

    @Autowired
    EmployeePositionService employeePositionService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    LocationService locationService;

    @Autowired
    CarService carService;

    @Test
    public void shouldFindAllEmployeesByLocation() {
        //given
        EmployeePositionTO position = createFirstPosition();
        EmployeeTO employee1 = createFirstEmployee();
        EmployeeTO employee2 = createSecondEmployee();
        EmployeeTO employee3 = createThirdEmployee();

        LocationTO location = createFirstLocation();

        employeePositionService.addEmployeePosition(position);
        EmployeeTO savedEmployee1 = employeeService.addEmployee(employee1);
        EmployeeTO savedEmployee2 = employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        LocationTO savedLocation = locationService.addLocation(location);

        //when
        locationService.addEmployeeToLocation(savedLocation.getId(), savedEmployee1);
        locationService.addEmployeeToLocation(savedLocation.getId(), savedEmployee2);

        List<EmployeeTO> employeesList = employeeService.findAllEmployeesByLocation(savedLocation.getId());

        EmployeeTO firstEmpFromLocation = employeesList.get(0);
        //then
        assertThat(employeesList.size()).isEqualTo(2);
        assertThat(firstEmpFromLocation.getName()).isEqualTo("Tomasz");
    }

    @Test
    public void shouldFindAllEmployeesByCarUnderKeepAndLocation() {
        //given
        EmployeePositionTO position = createFirstPosition();
        EmployeeTO employee1 = createFirstEmployee();
        EmployeeTO employee2 = createSecondEmployee();
        EmployeeTO employee3 = createThirdEmployee();

        LocationTO location = createFirstLocation();

        CarTO car = createFirstCar();

        employeePositionService.addEmployeePosition(position);
        EmployeeTO savedEmployee1 = employeeService.addEmployee(employee1);
        EmployeeTO savedEmployee2 = employeeService.addEmployee(employee2);
        EmployeeTO savedEmployee3 = employeeService.addEmployee(employee3);

        LocationTO savedLocation = locationService.addLocation(location);
        Long locationId = savedLocation.getId();

        CarTO savedCar = carService.addCar(car);
        Long carId = savedCar.getId();

        //when
        locationService.addEmployeeToLocation(locationId, savedEmployee1);
        locationService.addEmployeeToLocation(locationId, savedEmployee2);
        locationService.addEmployeeToLocation(locationId, savedEmployee3);

        carService.assignCarToCarer(savedCar, savedEmployee1);
        carService.assignCarToCarer(savedCar, savedEmployee3);

        List<EmployeeTO> employeesList = employeeService.findAllCarCarersByCarAndLocation(locationId, carId);

        EmployeeTO firstCarCarer = employeesList.get(0);
        EmployeeTO secondCarCarer = employeesList.get(1);
        //then
        assertThat(employeesList.size()).isEqualTo(2);
        assertThat(firstCarCarer.getName()).isEqualTo("Tomasz");
        assertThat(secondCarCarer.getSurname()).isEqualTo("Heller");
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

    private LocationTO createFirstLocation() {
        return LocationTO.builder()
                .phoneNumber(604567900)
                .address(Address.builder()
                        .city("Warszawa")
                        .street("Aleje 17")
                        .postalCode("02-300").build())
                .build();
    }

    private CarTO createFirstCar() {
        return CarTO.builder()
                .brand("Toyota")
                .model("Yarris")
                .carType("mini")
                .productionYear(2017)
                .color("white")
                .engineCapacity(1400)
                .power(90)
                .mileage(200)
                .build();
    }
}