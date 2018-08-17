package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.RentalEntity;
import com.capgemini.jstk.carrental.dto.RentalTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalTO map(RentalEntity rentalEntity) {
        if (rentalEntity != null) {
            return RentalTO.builder()
                    .id(rentalEntity.getId())
                    .startDate(rentalEntity.getStartDate())
                    .endDate(rentalEntity.getEndDate())
                    .totalCost(rentalEntity.getTotalCost())
                    .customerId(rentalEntity.getCustomer().getId())
                    .carId(rentalEntity.getCar().getId())
                    .startLocationId(rentalEntity.getStartLocation().getId())
                    .endLocationId(rentalEntity.getEndLocation().getId())
                    .build();
        }
        return null;
    }

    public RentalEntity map(RentalTO rentalTO) {
        if (rentalTO != null) {
            return RentalEntity.builder()
                    .id(rentalTO.getId())
                    .startDate(rentalTO.getStartDate())
                    .endDate(rentalTO.getEndDate())
                    .totalCost(rentalTO.getTotalCost())
                    .build();
        }
        return null;
    }

    public List<RentalTO> map2TO(List<RentalEntity> rentalEntities) {
        return rentalEntities.stream().map(this::map).collect(Collectors.toList());
    }
}
