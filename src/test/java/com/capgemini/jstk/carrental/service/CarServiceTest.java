package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    public void shouldFindCarById() {
        //given
        CarTO car = createFirstCar();

        CarTO savedCar = carService.addCar(car);
        //when
        CarTO carToCheck = carService.findCarById(savedCar.getId());

        //then
        assertThat(carToCheck.getBrand()).isEqualTo("Toyota");
        assertThat(carToCheck.getProductionYear()).isEqualTo(2017);
    }

    @Test
    public void shouldDeleteCar() {
        //given
        CarTO car1 = createFirstCar();
        CarTO car2 = createSecondCar();
        CarTO car3 = createThirdCar();

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
        CarTO car1 = createFirstCar();
        CarTO car2 = createSecondCar();
        CarTO car3 = createThirdCar();
        CarTO car4 = createFourthCar();

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
        CarTO car1= createFirstCar();

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
        EmployeePositionTO position = createFirstPosition();
        CarTO car1 = createFirstCar();
        CarTO car2 = createSecondCar();
        CarTO car3 = createThirdCar();

        EmployeeTO employee = createFirstEmployee();

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
    public void shouldFindCarsRentedByMoreThenTenDifferentCustomers() {
        //given
        CarTO car1 = carService.addCar(createFirstCar());
        CarTO car2 = carService.addCar(createSecondCar());

        LocationTO location1 = locationService.addLocation(createFirstLocation());
        LocationTO location2 = locationService.addLocation(createSecondLocation());

        CustomerTO customer1 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer2 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer3 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer4 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer5 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer6 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer7 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer8 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer9 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer10 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer11 = customerService.addCustomer(createFirstCustomer());

        RentalTO newRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-10"))
                .endDate(Date.valueOf("2018-01-15"))
                .totalCost(700)
                .customerId(customer1.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-15"))
                .endDate(Date.valueOf("2018-01-20"))
                .totalCost(700)
                .customerId(customer2.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent3 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-20"))
                .endDate(Date.valueOf("2018-01-25"))
                .totalCost(700)
                .customerId(customer3.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent4 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-25"))
                .endDate(Date.valueOf("2018-01-30"))
                .totalCost(700)
                .customerId(customer4.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent5 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-01"))
                .endDate(Date.valueOf("2018-02-05"))
                .totalCost(700)
                .customerId(customer5.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent6 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-05"))
                .endDate(Date.valueOf("2018-02-10"))
                .totalCost(700)
                .customerId(customer6.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location1.getId())
                .build();

        RentalTO newRent7 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-10"))
                .endDate(Date.valueOf("2018-02-15"))
                .totalCost(700)
                .customerId(customer7.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location1.getId())
                .build();

        RentalTO newRent8 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-15"))
                .endDate(Date.valueOf("2018-02-20"))
                .totalCost(700)
                .customerId(customer8.getId())
                .carId(car1.getId())
                .startLocationId(location2.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO newRent9 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-20"))
                .endDate(Date.valueOf("2018-02-25"))
                .totalCost(700)
                .customerId(customer9.getId())
                .carId(car1.getId())
                .startLocationId(location2.getId())
                .endLocationId(location1.getId())
                .build();

        RentalTO newRent10 = RentalTO.builder()
                .startDate(Date.valueOf("2018-02-25"))
                .endDate(Date.valueOf("2018-02-30"))
                .totalCost(700)
                .customerId(customer10.getId())
                .carId(car1.getId())
                .startLocationId(location2.getId())
                .endLocationId(location1.getId())
                .build();

        RentalTO newRent11 = RentalTO.builder()
                .startDate(Date.valueOf("2018-03-01"))
                .endDate(Date.valueOf("2018-03-15"))
                .totalCost(1200)
                .customerId(customer11.getId())
                .carId(car1.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO secondCarRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-10"))
                .endDate(Date.valueOf("2018-01-15"))
                .totalCost(700)
                .customerId(customer1.getId())
                .carId(car2.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
                .build();

        RentalTO secondCarRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-04-10"))
                .endDate(Date.valueOf("2018-04-15"))
                .totalCost(600)
                .customerId(customer6.getId())
                .carId(car2.getId())
                .startLocationId(location1.getId())
                .endLocationId(location2.getId())
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
        List<CarTO> carsList = carService.findCarsRentedByDifferentCustomers();
        CarTO carToCheck = carsList.get(0);
        //then
        assertThat(carsList.size()).isEqualTo(1);
        assertThat(carToCheck.getColor()).isEqualTo(car1.getColor());

    }

    @Test
    public void shouldFindCarsRentedInGivenPeriodOfTime() {
        //given
        CarTO car1 = carService.addCar(createFirstCar());
        CarTO car2 = carService.addCar(createSecondCar());
        CarTO car3 = carService.addCar(createThirdCar());
        CarTO car4 = carService.addCar(createFourthCar());

        LocationTO location = locationService.addLocation(createFirstLocation());

        CustomerTO customer1 = customerService.addCustomer(createFirstCustomer());
        CustomerTO customer2 = customerService.addCustomer(createSecondCustomer());
        CustomerTO customer3 = customerService.addCustomer(createSecondCustomer());

        RentalTO newRent1 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-14"))
                .endDate(Date.valueOf("2018-01-24"))
                .totalCost(700)
                .customerId(customer1.getId())
                .carId(car1.getId())
                .startLocationId(location.getId())
                .endLocationId(location.getId())
                .build();

        RentalTO newRent2 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-15"))
                .endDate(Date.valueOf("2018-01-20"))
                .totalCost(700)
                .customerId(customer2.getId())
                .carId(car2.getId())
                .startLocationId(location.getId())
                .endLocationId(location.getId())
                .build();

        RentalTO newRent3 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-16"))
                .endDate(Date.valueOf("2018-01-26"))
                .totalCost(700)
                .customerId(customer3.getId())
                .carId(car3.getId())
                .startLocationId(location.getId())
                .endLocationId(location.getId())
                .build();

        RentalTO newRent4 = RentalTO.builder()
                .startDate(Date.valueOf("2018-01-20"))
                .endDate(Date.valueOf("2018-01-23"))
                .totalCost(700)
                .customerId(customer2.getId())
                .carId(car4.getId())
                .startLocationId(location.getId())
                .endLocationId(location.getId())
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
        assertThat(carTOCheck.getId()).isEqualTo(car3.getId());

    }

    private EmployeePositionTO createFirstPosition() {
        return EmployeePositionTO.builder()
                .name("Kierownik")
                .build();
    }

    private EmployeeTO createFirstEmployee() {
        return EmployeeTO.builder()
                .name("Tomasz")
                .surname("Kot")
                .employeePosition(EmployeePositionTO.builder()
                    .id(1L)
                    .name("Kierownik").build())
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

    public CarTO createSecondCar() {
        return CarTO.builder()
                .brand("Toyota")
                .model("Yarris")
                .carType("mini")
                .productionYear(2017)
                .color("black")
                .engineCapacity(1400)
                .power(90)
                .mileage(150)
                .build();
    }

    private CarTO createThirdCar() {
        return CarTO.builder()
                .brand("BMW")
                .model("X1")
                .carType("suv")
                .productionYear(2017)
                .color("red")
                .engineCapacity(2000)
                .power(170)
                .mileage(10000)
                .build();
    }

    private CarTO createFourthCar() {
        return CarTO.builder()
                .brand("Toyota")
                .model("Avensis")
                .carType("sedan")
                .productionYear(2017)
                .color("blue")
                .engineCapacity(1900)
                .power(130)
                .mileage(1000)
                .build();
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

    private CustomerTO createFirstCustomer() {
        return CustomerTO.builder()
                .name("Adam")
                .surname("Kowalski")
                .eMail("ad@gmail.com")
                .mobilePhone(600253400)
                .creditCard("4684732941845524")
                .drivingLicense("120/155/17")
                .dateOfBirth(Date.valueOf("1980-11-23"))
                .address(Address.builder()
                        .street("Jackowskiego 30")
                        .postalCode("60-450")
                        .city("Poznan").build())
                .build();
    }

    private CustomerTO createSecondCustomer() {
        return CustomerTO.builder()
                .name("Robert")
                .surname("Kolarczyk")
                .eMail("robkol@gmail.com")
                .mobilePhone(700253400)
                .creditCard("2224732941845524")
                .drivingLicense("030/155/20")
                .dateOfBirth(Date.valueOf("1974-11-11"))
                .address(Address.builder()
                        .street("Jackowskiego 30")
                        .postalCode("54-100")
                        .city("Wroclaw").build())
                .build();
    }
}