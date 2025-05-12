package com.aib.websystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    @Query("select e from Event e where e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1")
    Page<Event> findByLocation(Location location, Pageable pageable);

    @Query("select e from Event e where (e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1) and e.status = ?2")
    Page<Event> findByLocationAndStatus(Location location, EventStatus status, Pageable pageable);

    // grouped events
    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e")
    Page<Object[]> findDistinct(Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.status = ?1")
    Page<Object[]> findDistinctByStatus(EventStatus status, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.fromLocation = ?1")
    Page<Object[]> findDistinctByFromLocation(Location fromLocation, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.fromLocation = ?1 and e.status = ?2")
    Page<Object[]> findDistinctByFromLocationAndStatus(Location fromLocation, EventStatus status, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.throughLocation = ?1")
    Page<Object[]> findDistinctByThroughLocation(Location throughLocation, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.throughLocation = ?1 and e.status = ?2")
    Page<Object[]> findDistinctByThroughLocationAndStatus(Location throughLocation, EventStatus status, Pageable pageable);

    List<Event> findByFruitAndTimePeriodAndFromLocationAndThroughLocation(Fruit fruit, Integer timePeriod, Location fromLocation, Location throughLocation);

    //
    @Query("select e.fruit, e.toLocation, SUM(e.quantity) from Event e where e.fromLocation = ?1 AND e.status = 0 GROUP BY e.fruit, e.toLocation")
    Page<Object[]> findByLocationGroupByFruit(Location location, Pageable pageable);

    @Query("select e.fruit, s.quantity, SUM(e.quantity) from Event e INNER JOIN Stock s ON e.fruit = s.fruit where s.location = ?1 AND e.fromLocation = ?1 AND e.status = 0 GROUP BY e.fruit, s.quantity")
    Page<Object[]> findFruitStockAndEventTotalByLocation(Location location, Pageable pageable);

    @Query("select e.fruit, s.quantity, SUM(e.quantity) from Event e INNER JOIN Stock s ON e.fruit = s.fruit where s.location = ?1 AND e.toLocation = ?1 AND e.status = 1 GROUP BY e.fruit, s.quantity")
    Page<Object[]> findFruitStockAndActiveEventTotalByLocation(Location location, Pageable pageable);
}
