package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.Location;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long>  {
    Page<Event> findByFromLocationOrToLocation(Location fromLocation, Location toLocation, Pageable pageable);
}
