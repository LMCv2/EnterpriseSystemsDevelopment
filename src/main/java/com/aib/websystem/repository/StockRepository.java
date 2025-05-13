package com.aib.websystem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>, PagingAndSortingRepository<Stock, Long> {
    Page<Stock> findByLocation(Location location, Pageable pageable);

    Page<Stock> findByLocationType(LocationType location, Pageable pageable);

    Page<Stock> findByFruitAndLocationType(Fruit fruit, LocationType type, Pageable pageable);

    Optional<Stock> findByFruitAndLocation(Fruit fruit, Location location);

    Page<Stock> findByFruitAndLocationTypeAndLocationCityAndIdNot(Fruit fruit, LocationType type, String city, Long id, Pageable pageable);
}
