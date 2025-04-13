package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>, PagingAndSortingRepository<Stock, Long> {
    Page<Stock> findByLocation(Location location,Pageable pageable);
}
