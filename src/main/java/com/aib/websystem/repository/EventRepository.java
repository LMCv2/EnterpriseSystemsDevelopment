package com.aib.websystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Location;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    @Query("select e from Event e where e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1")
    Page<Event> findByLocation(Location location, Pageable pageable);

    @Query("select e from Event e where (e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1) and e.status = ?2")
    Page<Event> findByLocationAndStatus(Location location, EventStatus status, Pageable pageable);

    @Query(value = "SELECT * FROM event e WHERE (e.status, e.through_location_id) IN " +
           "(SELECT status, through_location_id FROM event GROUP BY status, through_location_id)",
           nativeQuery = true)
    Page<List<Event>> findByTest(Pageable pageable);

    @Query("select e.fruit, e.toLocation, SUM(e.quantity) from Event e where e.fromLocation = ?1 AND e.status = 0 GROUP BY e.fruit, e.toLocation")
    Page<Object[]> findByLocationGroupByFruit(Location location, Pageable pageable);

    @Query("select e.fruit, s.quantity, SUM(e.quantity) from Event e INNER JOIN Stock s ON e.fruit = s.fruit where s.location = ?1 AND e.fromLocation = ?1 AND e.status = 0 GROUP BY e.fruit, s.quantity")
    Page<Object[]> findFruitStockAndEventTotalByLocation(Location location, Pageable pageable);

    @Query("select e.fruit, s.quantity, SUM(e.quantity) from Event e INNER JOIN Stock s ON e.fruit = s.fruit where s.location = ?1 AND e.toLocation = ?1 AND e.status = 1 GROUP BY e.fruit, s.quantity")
    Page<Object[]> findFruitStockAndActiveEventTotalByLocation(Location location, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.status = ?1 AND e.throughLocation = ?2")
    List<Event> findByStatusAndThroughLocation(EventStatus status, Location throughLocation);

    @Query("SELECT DISTINCT e.status, e.throughLocation FROM Event e")
    Page<Object[]> findDistinctStatusAndLocation(Pageable pageable);
}
