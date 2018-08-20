package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.CarEntity;
import com.capgemini.jstk.carrental.dto.CarTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public CarTO map(CarEntity carEntity) {
        if (carEntity != null) {
            return CarTO.builder()
                    .id(carEntity.getId())
                    .brand(carEntity.getBrand())
                    .model(carEntity.getModel())
                    .carType(carEntity.getCarType())
                    .productionYear(carEntity.getProductionYear())
                    .color(carEntity.getColor())
                    .engineCapacity(carEntity.getEngineCapacity())
                    .power(carEntity.getPower())
                    .mileage(carEntity.getMileage())
                    .build();
        }
        return null;
    }

    public CarEntity map(CarTO carTO) {
        if (carTO != null) {
            return CarEntity.builder()
                    .id(carTO.getId())
                    .brand(carTO.getBrand())
                    .model(carTO.getModel())
                    .carType(carTO.getCarType())
                    .productionYear(carTO.getProductionYear())
                    .color(carTO.getColor())
                    .engineCapacity(carTO.getEngineCapacity())
                    .power(carTO.getPower())
                    .mileage(carTO.getMileage())
                    .build();
        }
        return null;
    }

    public List<CarTO> map2TO(List<CarEntity> carEntities) {
        return carEntities.stream().map(this::map).collect(Collectors.toList());
    }

}
