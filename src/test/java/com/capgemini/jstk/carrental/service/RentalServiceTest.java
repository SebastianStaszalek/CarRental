package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.CarTO;
import com.capgemini.jstk.carrental.dto.CustomerTO;
import com.capgemini.jstk.carrental.dto.LocationTO;
import com.capgemini.jstk.carrental.dto.RentalTO;
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
public class RentalServiceTest {

    @Autowired
    RentalService rentalService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CarService carService;

    @Autowired
    LocationService locationService;

    @Test
    public void shouldAddRentalWithAllRelations() {
        //given
        CustomerTO customer = createFirtsCustomer();
        CarTO car = createFirstCar();
        LocationTO startLocation = createFirstLocation();
        LocationTO endLocation = createSecondLocation();

        CustomerTO savedCustomer = customerService.addCustomer(customer);
        CarTO savedCar = carService.addCar(car);
        LocationTO savedStartLocation = locationService.addLocation(startLocation);
        LocationTO savedEndLocation = locationService.addLocation(endLocation);

        RentalTO newRental = RentalTO.builder()
                .startDate(Date.valueOf("2018-08-10"))
                .endDate(Date.valueOf("2018-08-15"))
                .totalCost(700)
                .customerId(savedCustomer.getId())
                .carId(savedCar.getId())
                .startLocationId(savedStartLocation.getId())
                .endLocationId(savedEndLocation.getId())
                .build();

        //when
        RentalTO savedRental = rentalService.addRental(newRental);

        Long customerId = savedRental.getCustomerId();
        Long carId = savedRental.getCarId();
        Long startRentalId = savedRental.getStartLocationId();
        Long endRentalId = savedRental.getEndLocationId();

        //then
        assertThat(savedRental.getId()).isNotNull();
        assertThat(savedRental.getTotalCost()).isEqualTo(700);
        assertThat(savedCustomer.getId()).isEqualTo(customerId);
        assertThat(savedCar.getId()).isEqualTo(carId);
        assertThat(savedStartLocation.getId()).isEqualTo(startRentalId);
        assertThat(savedEndLocation.getId()).isEqualTo(endRentalId);

    }

    @Test
    public void shouldRemoveRentalCascadedWhenRemovingCar() {
        //given
        CustomerTO customer = createFirtsCustomer();
        CarTO car = createFirstCar();
        LocationTO startLocation = createFirstLocation();
        LocationTO endLocation = createSecondLocation();

        CustomerTO savedCustomer = customerService.addCustomer(customer);
        CarTO savedCar = carService.addCar(car);
        LocationTO savedStartLocation = locationService.addLocation(startLocation);
        LocationTO savedEndLocation = locationService.addLocation(endLocation);

        RentalTO newRental = RentalTO.builder()
                .startDate(Date.valueOf("2018-08-10"))
                .endDate(Date.valueOf("2018-08-15"))
                .totalCost(700)
                .customerId(savedCustomer.getId())
                .carId(savedCar.getId())
                .startLocationId(savedStartLocation.getId())
                .endLocationId(savedEndLocation.getId())
                .build();

        RentalTO savedRental = rentalService.addRental(newRental);

        //when
        carService.deleteCar(savedCar);

        RentalTO rentalToCheck = rentalService.findRentalById(savedRental.getId());
        List<RentalTO> rentalsList = rentalService.getAllRentals();

        CustomerTO customerToCheck = customerService.findCustomerById(savedCustomer.getId());
        LocationTO locationToCheck = locationService.findLocationById(savedStartLocation.getId());
        LocationTO locationToCheck2 = locationService.findLocationById(savedEndLocation.getId());
        //then
        assertThat(rentalsList).isEmpty();
        assertThat(rentalToCheck).isNull();
        assertThat(customerToCheck.getId()).isNotNull();
        assertThat(locationToCheck.getId()).isNotNull();
        assertThat(locationToCheck2.getId()).isNotNull();
    }

    private CustomerTO createFirtsCustomer() {
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
}