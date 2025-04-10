package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.repository.FruitRepository;

@Controller
@RequestMapping(path = "/fruit")
public class FruitController {
    @Autowired
    private FruitRepository fruitRepository;

    @GetMapping("")
    public String getFruits(Model model) {
        model.addAttribute("fruits", fruitRepository.findAll());
        return "/pages/fruit/index";
    }

    @PostMapping("/add-fruit")
    public String addFruit(@RequestParam String fruitName) {
        try {
            Fruit fruit = new Fruit(fruitName);
            fruitRepository.save(fruit);
            return "Fruit added successfully";
        } catch (Exception e) {
            return "Error adding fruit: " + e.getMessage();
        }
    }
}
