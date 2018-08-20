package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
import com.capgemini.jstk.carrental.dto.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TestTO {

    public EmployeePositionTO createFirstPosition() {
        return EmployeePositionTO.builder()
                .name("Kierownik")
                .build();
    }

    public EmployeePositionTO createSecondPosition() {
        return EmployeePositionTO.builder()
                .name("Kierownik")
                .build();
    }

    public EmployeeTO createFirstEmployee() {
        return EmployeeTO.builder()
                .name("Tomasz")
                .surname("Kot")
                .employeePosition(EmployeePositionTO.builder()
                        .id(1L)
                        .name("Kierownik").build())
                .build();
    }

    public EmployeeTO createSecondEmployee() {
        return EmployeeTO.builder()
                .name("Lukasz")
                .surname("Majek")
                .employeePosition(EmployeePositionTO.builder()
                        .id(1L)
                        .name("Sprzedawca").build())
                .build();
    }

    public EmployeeTO createThirdEmployee() {
        return EmployeeTO.builder()
                .name("Maja")
                .surname("Heller")
                .employeePosition(EmployeePositionTO.builder()
                        .id(2L)
                        .name("Sprzedawca").build())
                .build();
    }

    public CarTO createFirstCar() {
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

    public CarTO createThirdCar() {
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

    public CarTO createFourthCar() {
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

    public LocationTO createFirstLocation() {
        return LocationTO.builder()
                .phoneNumber(604567880)
                .address(Address.builder()
                        .city("Poznan")
                        .street("Wronska 17")
                        .postalCode("60-754").build())
                .build();
    }

    public LocationTO createSecondLocation() {
        return LocationTO.builder()
                .phoneNumber(700567880)
                .address(Address.builder()
                        .city("Gdansk")
                        .street("Rzemieslnicza 5")
                        .postalCode("46-200").build())
                .build();
    }

    public LocationTO createThirdLocation() {
        return LocationTO.builder()
                .phoneNumber(600200100)
                .address(Address.builder()
                        .city("Wroclaw")
                        .street("Polna 10")
                        .postalCode("64-120").build())
                .build();
    }

    public CustomerTO createFirstCustomer() {
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

    public CustomerTO createSecondCustomer() {
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
