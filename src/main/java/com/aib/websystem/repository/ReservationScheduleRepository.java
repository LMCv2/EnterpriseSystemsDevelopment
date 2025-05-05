package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.ReservationSchedule;

public interface ReservationScheduleRepository extends CrudRepository<ReservationSchedule, Long>, PagingAndSortingRepository<ReservationSchedule, Long> {
    
}
