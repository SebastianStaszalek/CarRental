package com.capgemini.jstk.carrental.service;

import com.capgemini.jstk.carrental.domain.Address;
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
public class LocationServiceTest {

    @Autowired
    LocationService locationService;

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
}