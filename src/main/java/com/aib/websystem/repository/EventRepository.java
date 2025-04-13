package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long>  {
}
