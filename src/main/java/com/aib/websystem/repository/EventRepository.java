package com.aib.websystem.repository;

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

    @Query("select e from Event e where e.fromLocation = ?1 OR e.toLocation = ?1 OR e.OriginalFromLocation = ?1 OR e.FinalToLocation = ?1")
    Page<Event> findByLocation(Location location, Pageable pageable);

    @Query("select e from Event e where (e.fromLocation = ?1 OR e.toLocation = ?1 OR e.OriginalFromLocation = ?1 OR e.FinalToLocation = ?1) and e.status = ?2")
    Page<Event> findByLocationAndStatus(Location location, EventStatus status, Pageable pageable);

    @Query("select e.fruit, e.toLocation, SUM(e.quantity) from Event e where e.fromLocation = ?1 OR e.toLocation = ?1 OR e.OriginalFromLocation = ?1 OR e.FinalToLocation = ?1 GROUP BY e.fruit, e.toLocation")
    Page<Object[]> findByLocationGroupByFruit(Location location, Pageable pageable);

    @Query("select e.fruit, SUM(e.quantity) from Event e where e.fromLocation = ?1 OR e.toLocation = ?1 OR e.OriginalFromLocation = ?1 OR e.FinalToLocation = ?1 GROUP BY e.fruit")
    Page<Object[]> sumQuantityByLocationGroupByFruit(Location location, Pageable pageable);
}
