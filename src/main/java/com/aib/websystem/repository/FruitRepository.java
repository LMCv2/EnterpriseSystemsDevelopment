package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Fruit;

public interface FruitRepository extends CrudRepository<Fruit, Long>, PagingAndSortingRepository<Fruit, Long> {
    Page<Fruit> findByDeletedFalse(Pageable pageable);
    Page<Fruit> findByDeletedTrue(Pageable pageable);
}
