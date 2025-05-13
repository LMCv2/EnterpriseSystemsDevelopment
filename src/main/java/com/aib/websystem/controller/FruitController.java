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
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.repository.FruitRepository;
import com.aib.websystem.repository.StockRepository;
import com.aib.websystem.service.StockService;

@Controller
@RequestMapping("/fruit")
public class FruitController {
    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public String getFruitsPage(@RequestParam(defaultValue = "all") String type, @RequestParam(defaultValue = "1") Integer page, Model model) {
        if (type.equals("all")) {
            model.addAttribute("fruits", fruitRepository.findByDeletedFalse(PageRequest.of(page - 1, 10)));
        } else if (type.equals("deleted")) {
            model.addAttribute("fruits", fruitRepository.findByDeletedTrue(PageRequest.of(page - 1, 10)));
        }
        return "/pages/fruit/index";
    }

    @GetMapping("/{id}")
    public String getFruitPage(@PathVariable Long id, Model model) {
        Fruit fruit = fruitRepository.findById(id).orElse(null);
        model.addAttribute("fruit", fruit);
        model.addAttribute("stocks", stockRepository.findByFruitAndLocationType(fruit, LocationType.SOURCE_WAREHOUSE, PageRequest.of(0, 10)));
        return "/pages/fruit/view";
    }

    @GetMapping("/create")
    public String createFruitPage(Model model) {
        model.addAttribute("fruit", new Fruit());
        return "/pages/fruit/create";
    }

    @GetMapping("/{id}/edit")
    public String editFruitPage(@PathVariable Long id, Model model) {
        model.addAttribute("fruit", fruitRepository.findById(id).orElse(null));
        return "/pages/fruit/edit";
    }

    @PostMapping("/create")
    public String createFruit(Fruit fruit) {
        fruitRepository.save(fruit);
        stockService.addFruitToAllLocation(fruit);
        return "redirect:/fruit/";
    }

    @PutMapping("/{id}/edit")
    public String editFruit(@PathVariable Long id, Fruit newFruit) {
        if (fruitRepository.existsById(id)) {
            Fruit originFruit = fruitRepository.findById(id).get();
            originFruit.setName(newFruit.getName());
            originFruit.setDeleted(newFruit.getDeleted());
            fruitRepository.save(originFruit);
        }
        return "redirect:/fruit/";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteFruit(@PathVariable Long id) {
        if (fruitRepository.existsById(id)) {
            Fruit fruit = fruitRepository.findById(id).get();
            fruit.setDeleted(true);
            fruitRepository.save(fruit);
        }
        return "redirect:/fruit/";
    }
}
