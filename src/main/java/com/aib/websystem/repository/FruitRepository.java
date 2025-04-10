package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.aib.websystem.entity.Fruit;

public interface FruitRepository extends CrudRepository<Fruit, Long> {
}
