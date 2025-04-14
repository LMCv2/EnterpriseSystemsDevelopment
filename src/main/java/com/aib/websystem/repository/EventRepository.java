package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Location;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long>  {
    Page<Event> findByFromLocationOrToLocation(Location fromLocation, Location toLocation, Pageable pageable);

    Page<Event> findByStatusAndToLocation(EventStatus status, Location toLocation, Pageable pageable);

    Page<Event> findByStatusAndFromLocation(EventStatus status, Location toLocation, Pageable pageable);

    // @Query("SELECT * FROM event WHERE status = :status AND (from_location = :fromLocation OR to_location = :toLocation)")
    // Page<Event> findByStatusAndFromLocationOrToLocation(EventStatus status, Location fromLocation, Location toLocation,
    //         Pageable pageable);
}
