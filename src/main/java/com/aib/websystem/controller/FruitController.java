package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        model.addAttribute("fruits", fruitRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/fruit/index";
    }

    @GetMapping("/new")
    public String createAccount() {
        return "/pages/fruit/new";
    }

    @PostMapping("/update/{id}")
    public String test(@PathVariable Long id, @RequestParam String fruitName) {
        if (fruitRepository.existsById(id)) {
            Fruit fruit = fruitRepository.findById(id).get();
            fruit.setName(fruitName);
            fruitRepository.save(fruit);
        }
        return "redirect:/fruit";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@RequestParam Long id) {
        if (fruitRepository.existsById(id)) {
            fruitRepository.deleteById(id);
        }
        return "redirect:/fruit";
    }

    @GetMapping("/{id}")
    public String getAccount(@PathVariable Long id, Model model) {
        model.addAttribute("fruit", fruitRepository.findById(id).orElse(null));
        return "/pages/fruit/edit";
    }

    @PostMapping("/add-fruit")
    public String addFruit(@RequestParam String fruitName) {
        try {
            Fruit fruit = new Fruit(fruitName);
            fruitRepository.save(fruit);
            return "redirect:/fruit";
        } catch (Exception e) {
            return "redirect:/fruit";
        }
    }

    
}
