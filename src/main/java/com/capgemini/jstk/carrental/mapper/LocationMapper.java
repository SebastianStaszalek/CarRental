package com.capgemini.jstk.carrental.mapper;

import com.capgemini.jstk.carrental.domain.LocationEntity;
import com.capgemini.jstk.carrental.dto.LocationTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public LocationTO map(LocationEntity locationEntity) {
        if (locationEntity != null) {
            return LocationTO.builder()
                    .id(locationEntity.getId())
                    .phoneNumber(locationEntity.getPhoneNumber())
                    .address(locationEntity.getAddress())
                    .build();
        }
        return null;
    }

    public LocationEntity map(LocationTO locationTO) {
        if (locationTO != null) {
            return LocationEntity.builder()
                    .id(locationTO.getId())
                    .phoneNumber(locationTO.getPhoneNumber())
                    .address(locationTO.getAddress())
                    .build();
        }
        return null;
    }

    public List<LocationTO> map2TO(List<LocationEntity> locationEntities) {
        return locationEntities.stream().map(this::map).collect(Collectors.toList());
    }

}
