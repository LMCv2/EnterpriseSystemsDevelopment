package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.repository.FruitRepository;

@Controller
@RequestMapping("/fruit")
public class FruitController {
    @Autowired
    private FruitRepository fruitRepository;

    @GetMapping("/")
    public String getFruitsPage(Model model) {
        model.addAttribute("fruits", fruitRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/fruit/index";
    }

    @GetMapping("/new")
    public String createFruitPage(Model model) {
        model.addAttribute("fruit", new Fruit());
        return "/pages/fruit/new";
    }

    @GetMapping("/{id}")
    public String updateFruitPage(@PathVariable Long id, Model model) {
        model.addAttribute("fruit", fruitRepository.findById(id).orElse(null));
        return "/pages/fruit/edit";
    }

    @PostMapping("/new")
    public String createFruit(Fruit fruit) {
        fruitRepository.save(fruit);
        return "redirect:/fruit/";
    }

    @PutMapping("/{id}")
    public String updateFruit(@PathVariable Long id, Fruit newFruit) {
        if (fruitRepository.existsById(id)) {
            Fruit originFruit = fruitRepository.findById(id).get();
            originFruit.setName(newFruit.getName());
            fruitRepository.save(originFruit);
        }
        return "redirect:/fruit/";
    }

    @DeleteMapping("/{id}")
    public String deleteLocation(@PathVariable Long id) {
        if (fruitRepository.existsById(id)) {
            fruitRepository.deleteById(id);
        }
        return "redirect:/fruit/";
    }
}
