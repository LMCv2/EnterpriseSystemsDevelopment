package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Stock;

public interface StockRepository extends JpaSpecificationExecutor<Long>, CrudRepository<Stock, Long>, PagingAndSortingRepository<Stock, Long> {
    Page<Stock> findByLocation(Location location, Pageable pageable);

    Page<Stock> findByLocationType(LocationType location, Pageable pageable);

    Page<Stock> findByFruitAndLocationType(Fruit fruit, LocationType type, Pageable pageable);

    Page<Stock> findByFruitAndLocationTypeAndLocationCityNameAndIdNot(Fruit fruit, LocationType type, String cityName, Long id, Pageable pageable);
}
