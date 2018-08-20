package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.CarTO;
import com.capgemini.jstk.carrental.dto.EmployeePositionTO;
import com.capgemini.jstk.carrental.dto.EmployeeTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import com.capgemini.jstk.carrental.exception.EmployeeNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
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

    @Autowired
    TestTO testTO;

    @Test
    public void shouldAddEmployee() {
        //given
        EmployeeTO employee = testTO.createFirstEmployee();

        //when
        EmployeeTO savedEmployee = employeeService.addEmployee(employee);

        //then
        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    public void shouldFindEmployeeById() {
        //given
        EmployeeTO employee = testTO.createFirstEmployee();

        EmployeeTO savedEmployee = employeeService.addEmployee(employee);

        //when
        EmployeeTO employeeToCheck = employeeService.findEmployeeById(savedEmployee.getId());

        //then
        assertThat(employee.getSurname()).isEqualTo(employeeToCheck.getSurname());
        assertThat(employee.getEmployeePosition()).isEqualTo(employee.getEmployeePosition());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void shouldThrowEmployeeNotFoundException() {
        //given
        EmployeeTO employee = testTO.createFirstEmployee();
        employeeService.addEmployee(employee);

        //when
        employeeService.findEmployeeById(2L);
    }

    @Test
    public void shouldFindAllEmployeesByLocation() {
        //given
        EmployeePositionTO position = testTO.createFirstPosition();
        EmployeeTO employee1 = testTO.createFirstEmployee();
        EmployeeTO employee2 = testTO.createSecondEmployee();
        EmployeeTO employee3 = testTO.createThirdEmployee();

        LocationTO location = testTO.createFirstLocation();

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
        EmployeePositionTO position = testTO.createFirstPosition();
        EmployeeTO employee1 = testTO.createFirstEmployee();
        EmployeeTO employee2 = testTO.createSecondEmployee();
        EmployeeTO employee3 = testTO.createThirdEmployee();

        LocationTO location = testTO.createFirstLocation();

        CarTO car = testTO.createFirstCar();

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

    @Test
    public void shouldFindEmployeesByPosition() {
        EmployeePositionTO position1 = testTO.createFirstPosition();
        EmployeePositionTO position2 = testTO.createSecondPosition();

        EmployeeTO employee1 = testTO.createFirstEmployee();
        EmployeeTO employee2 = testTO.createSecondEmployee();
        EmployeeTO employee3 = testTO.createThirdEmployee();

        EmployeePositionTO savedPosition = employeePositionService.addEmployeePosition(position1);
        employeePositionService.addEmployeePosition(position2);

        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);

        //when
        List<EmployeeTO> employeesList = employeeService.findEmployeesByPosition(savedPosition.getId());

        //then
        assertThat(employeesList.size()).isEqualTo(2);
    }

}