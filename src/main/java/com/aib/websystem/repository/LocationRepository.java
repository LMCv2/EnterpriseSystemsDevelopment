package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;

public interface LocationRepository extends CrudRepository<Location, Long>, PagingAndSortingRepository<Location, Long> {
    Page<Location> findByDeletedFalse(Pageable pageable);

    Iterable<Location> findByTypeAndDeletedFalse(LocationType location);

    Page<Location> findByTypeAndDeletedFalse(LocationType location, Pageable pageable);

    Page<Location> findByDeletedTrue(Pageable pageable);

    Page<Location> findByCountryAndCityAndType(String country, String city, LocationType locationType, Pageable pageable);
}
