package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>, PagingAndSortingRepository<Stock, Long> {
}
