package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.repository.FruitRepository;

@RestController
public class FruitController {
    @Autowired
    private FruitRepository fruitRepository;
    @GetMapping("/get-fruits")
    public Iterable<Fruit> getFruits() {
        return fruitRepository.findAll();
    }
    @PostMapping("/add-fruit")
    public String addFruit(@RequestParam String fruitName) {
        try{
            Fruit fruit = new Fruit();
            fruit.setName(fruitName);
            fruitRepository.save(fruit);
            return "Fruit added successfully";
        }catch(Exception e){
            return "Error adding fruit: " + e.getMessage();
        }
        
    }
}
