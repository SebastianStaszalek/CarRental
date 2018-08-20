package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dao.CarDao;
import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CarServiceTest {

    @Autowired
    CarService carService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeePositionService employeePositionService;

    @Autowired
    RentalService rentalService;

    @Autowired
    LocationService locationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TestTO testTO;

    @Autowired
    CarDao carDao;

    @Test
    public void shouldAddCar() {
        //given
        CarTO car = testTO.createFirstCar();

        //when
        CarTO savedCar = carService.addCar(car);

        //then
        assertThat(savedCar.getId()).isNotNull();
    }

    @Test
    public void shouldFindCarById() {
        //given
        CarTO car = testTO.createFirstCar();

        CarTO savedCar = carService.addCar(car);
        //when
        CarTO carToCheck = carService.findCarById(savedCar.getId());

        //then
        assertThat(carToCheck.getBrand()).isEqualTo("Toyota");
        assertThat(carToCheck.getProductionYear()).isEqualTo(2017);
    }

    @Test
    public void shouldGetListOfAllCars() {
        //given
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();
        CarTO car3 = testTO.createThirdCar();

        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);

        //when
        List<CarTO> carsList = carService.getAllCars();

        //then
        assertThat(carsList).isNotEmpty();
        assertThat(carsList.size()).isEqualTo(3);
    }

    @Test
    public void shouldDeleteCar() {
        //given
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();
        CarTO car3 = testTO.createThirdCar();

        carService.addCar(car1);
        CarTO carToCheck = carService.addCar(car2);
        carService.addCar(car3);

        //when
        carService.deleteCar(carToCheck);
        List<CarTO> carsList = carService.getAllCars();

        //then
        assertThat(carService.findCarById(carToCheck.getId())).isNull();
        assertThat(carsList.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindCarByTypeAndBrand() {
        //given
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();
        CarTO car3 = testTO.createThirdCar();
        CarTO car4 = testTO.createFourthCar();

        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);
        carService.addCar(car4);

        CarTO searchCar = CarTO.builder().brand("Toyota").carType("mini").build();

        //when
        List<CarTO> carsList = carService.findCarByTypeAndBrand(searchCar);

        //then
        assertThat(carsList.size()).isEqualTo(2);
        assertThat(carsList.get(0).getBrand()).isEqualTo("Toyota");
        assertThat(carsList.get(1).getBrand()).isEqualTo("Toyota");
    }

    @Test
    public void shouldUpdateCar() {
        //given
        CarTO car1= testTO.createFirstCar();

        CarTO savedCar = carService.addCar(car1);

        CarTO carToUpdate = CarTO.builder()
                .id(savedCar.getId())
                .brand("BMW")
                .model("X1")
                .carType("suv")
                .productionYear(2017)
                .color("grey")
                .engineCapacity(2000)
                .power(170)
                .mileage(12000)
                .build();

        //when
        carService.updateCar(carToUpdate);
        CarTO updatedCar = carService.findCarById(savedCar.getId());

        //then
        assertThat(updatedCar.getBrand()).isEqualTo("BMW");
        assertThat(updatedCar.getMileage()).isEqualTo(12000);
        assertThat(updatedCar.getColor()).isEqualTo("grey");
    }

    @Test
    public void shouldFindCarByCarer() {
        //given
        EmployeePositionTO position = testTO.createFirstPosition();
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();
        CarTO car3 = testTO.createThirdCar();

        EmployeeTO employee = testTO.createFirstEmployee();

        CarTO savedCar = carService.addCar(car1);
        carService.addCar(car2);
        CarTO savedCar3 = carService.addCar(car3);

        employeePositionService.addEmployeePosition(position);
        EmployeeTO savedEmployee = employeeService.addEmployee(employee);

        //when
        carService.assignCarToCarer(savedCar, savedEmployee);
        carService.assignCarToCarer(savedCar3, savedEmployee);

        List<CarTO> carList = carService.findCarByCarer(savedEmployee);
        CarTO carToCheck = carList.get(0);
        CarTO carToCheck2 = carList.get(1);

        //then
        assertThat(carToCheck.getBrand()).isEqualTo("Toyota");
        assertThat(carToCheck2.getBrand()).isEqualTo("BMW");
        assertThat(carList.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindCarsRentedByMoreThanTenDifferentCustomers() {
        //given
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();

        LocationTO location1 = testTO.createFirstLocation();
        LocationTO location2 = testTO.createSecondLocation();

        CustomerTO customer = testTO.createFirstCustomer();


        CarTO savedCar1 = carService.addCar(car1);
        CarTO savedCar2 = carService.addCar(car2);

        LocationTO savedLocation1 = locationService.addLocation(location1);
        LocationTO savedLocation2 = locationService.addLocation(location2);

        CustomerTO savedCustomer1 = customerService.addCustomer(customer);
        CustomerTO savedCustomer2 = customerService.addCustomer(customer);
        CustomerTO savedCustomer3 = customerService.addCustomer(customer);
        CustomerTO savedCustomer4 = customerService.addCustomer(customer);
        CustomerTO savedCustomer5 = customerService.addCustomer(customer);
        CustomerTO savedCustomer6 = customerService.addCustomer(customer);
        CustomerTO savedCustomer7 = customerService.addCustomer(customer);
        CustomerTO savedCustomer8 = customerService.addCustomer(customer);
        CustomerTO savedCustomer9 = customerService.addCustomer(customer);
        CustomerTO savedCustomer10 = customerService.addCustomer(customer);
        CustomerTO savedCustomer11 = customerService.addCustomer(customer);

        RentalTO newRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-10"))
                .endDate(Date.valueOf("2018-01-15"))
                .totalCost(700)
                .customerId(savedCustomer1.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-15"))
                .endDate(Date.valueOf("2018-01-20"))
                .totalCost(700)
                .customerId(savedCustomer2.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent3 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-20"))
                .endDate(Date.valueOf("2018-01-25"))
                .totalCost(700)
                .customerId(savedCustomer3.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent4 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-25"))
                .endDate(Date.valueOf("2018-01-30"))
                .totalCost(700)
                .customerId(savedCustomer4.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent5 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-01"))
                .endDate(Date.valueOf("2018-02-05"))
                .totalCost(700)
                .customerId(savedCustomer5.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent6 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-05"))
                .endDate(Date.valueOf("2018-02-10"))
                .totalCost(700)
                .customerId(savedCustomer6.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation1.getId())
                .build();

        RentalTO newRent7 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-10"))
                .endDate(Date.valueOf("2018-02-15"))
                .totalCost(700)
                .customerId(savedCustomer7.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation1.getId())
                .build();

        RentalTO newRent8 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-15"))
                .endDate(Date.valueOf("2018-02-20"))
                .totalCost(700)
                .customerId(savedCustomer8.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation2.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO newRent9 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-20"))
                .endDate(Date.valueOf("2018-02-25"))
                .totalCost(700)
                .customerId(savedCustomer9.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation2.getId())
                .endLocationId(savedLocation1.getId())
                .build();

        RentalTO newRent10 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-25"))
                .endDate(Date.valueOf("2018-02-30"))
                .totalCost(700)
                .customerId(savedCustomer10.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation2.getId())
                .endLocationId(savedLocation1.getId())
                .build();

        RentalTO newRent11 = RentalTO.builder()
                .startDate(Date.valueOf("2018-03-01"))
                .endDate(Date.valueOf("2018-03-15"))
                .totalCost(1200)
                .customerId(savedCustomer11.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO secondCarRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-10"))
                .endDate(Date.valueOf("2018-01-15"))
                .totalCost(700)
                .customerId(savedCustomer1.getId())
                .carId(savedCar2.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        RentalTO secondCarRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-04-10"))
                .endDate(Date.valueOf("2018-04-15"))
                .totalCost(600)
                .customerId(savedCustomer6.getId())
                .carId(savedCar2.getId())
                .startLocationId(savedLocation1.getId())
                .endLocationId(savedLocation2.getId())
                .build();

        rentalService.addRental(newRent1);
        rentalService.addRental(newRent2);
        rentalService.addRental(newRent3);
        rentalService.addRental(newRent4);
        rentalService.addRental(newRent5);
        rentalService.addRental(newRent6);
        rentalService.addRental(newRent7);
        rentalService.addRental(newRent8);
        rentalService.addRental(newRent9);
        rentalService.addRental(newRent10);
        rentalService.addRental(newRent11);
        rentalService.addRental(secondCarRent1);
        rentalService.addRental(secondCarRent2);

        //when
        List<CarTO> carsList = carService.findCarsRentedByDifferentCustomers(10L);
        CarTO carToCheck = carsList.get(0);

        //then
        assertThat(carsList.size()).isEqualTo(1);
        assertThat(carToCheck.getColor()).isEqualTo(car1.getColor());

    }

    @Test
    public void shouldFindCarsRentedInGivenPeriodOfTime() {
        //given
        CarTO car1 = testTO.createFirstCar();
        CarTO car2 = testTO.createSecondCar();
        CarTO car3 = testTO.createThirdCar();
        CarTO car4 = testTO.createFourthCar();

        LocationTO location = testTO.createFirstLocation();

        CustomerTO customer1 = testTO.createFirstCustomer();
        CustomerTO customer2 = testTO.createSecondCustomer();
        CustomerTO customer3 = testTO.createSecondCustomer();

        CarTO savedCar1 = carService.addCar(car1);
        CarTO savedCar2 = carService.addCar(car2);
        CarTO savedCar3 = carService.addCar(car3);
        CarTO savedCar4 = carService.addCar(car4);

        LocationTO savedLocation = locationService.addLocation(location);

        CustomerTO savedCustomer1 = customerService.addCustomer(customer1);
        CustomerTO savedCustomer2 = customerService.addCustomer(customer2);
        CustomerTO savedCustomer3 = customerService.addCustomer(customer3);


        RentalTO newRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-14"))
                .endDate(Date.valueOf("2018-01-24"))
                .totalCost(700)
                .customerId(savedCustomer1.getId())
                .carId(savedCar1.getId())
                .startLocationId(savedLocation.getId())
                .endLocationId(savedLocation.getId())
                .build();

        RentalTO newRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-15"))
                .endDate(Date.valueOf("2018-01-20"))
                .totalCost(700)
                .customerId(savedCustomer2.getId())
                .carId(savedCar2.getId())
                .startLocationId(savedLocation.getId())
                .endLocationId(savedLocation.getId())
                .build();

        RentalTO newRent3 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-16"))
                .endDate(Date.valueOf("2018-01-26"))
                .totalCost(700)
                .customerId(savedCustomer3.getId())
                .carId(savedCar3.getId())
                .startLocationId(savedLocation.getId())
                .endLocationId(savedLocation.getId())
                .build();

        RentalTO newRent4 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-20"))
                .endDate(Date.valueOf("2018-01-23"))
                .totalCost(700)
                .customerId(savedCustomer2.getId())
                .carId(savedCar4.getId())
                .startLocationId(savedLocation.getId())
                .endLocationId(savedLocation.getId())
                .build();

        rentalService.addRental(newRent1);
        rentalService.addRental(newRent2);
        rentalService.addRental(newRent3);
        rentalService.addRental(newRent4);

        //when
        Date from = Date.valueOf("2018-01-16");
        Date to = Date.valueOf("2018-01-25");

        List<CarTO> carsList = carService.findCarsRentedInGivenPeriodOfTime(from, to);
        CarTO carTOCheck = carsList.get(0);

        //then
        assertThat(carsList.size()).isEqualTo(1);
        assertThat(carTOCheck.getId()).isEqualTo(savedCar3.getId());

    }

    //TODO: nie wykrywa wyjatku??
    @Test//(expected = ObjectOptimisticLockingFailureException.class)
    @Transactional
    public void shouldThrowOptimisticLockingException() {

        // given
        CarEntity carEntity = CarEntity.builder()
                .brand("Toyota")
                .model("Yarris")
                .carType("mini")
                .productionYear(2017)
                .color("white")
                .engineCapacity(1400)
                .power(90)
                .mileage(200)
                .build();

        carEntity = carDao.save(carEntity);
        carDao.flush();
        carDao.detach(carEntity);
        carEntity.setModel("Mazda");

        CarEntity newClientEntity = carDao.findOne(carEntity.getId());

        newClientEntity.setModel("Toyota");
        carDao.save(newClientEntity);
        carDao.flush();


        // when
        CarEntity carEntity2 = carDao.update(carEntity);
        carDao.save(carEntity2);

    }





}