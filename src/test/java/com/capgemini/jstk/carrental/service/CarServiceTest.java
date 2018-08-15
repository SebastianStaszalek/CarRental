package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.dto.CarTO;
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
public class CarServiceTest {

    @Autowired
    CarService carService;

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
                .id(1L)
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
}