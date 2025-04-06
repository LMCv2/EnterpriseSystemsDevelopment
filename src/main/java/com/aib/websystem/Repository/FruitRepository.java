package com.aib.websystem.Repository;

import org.springframework.data.repository.CrudRepository;

import com.aib.websystem.Entity.Fruit;

public interface FruitRepository extends CrudRepository<Fruit, Long> {

}