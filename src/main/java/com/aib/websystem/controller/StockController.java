package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aib.websystem.repository.StockRepository;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("")
    public String getFruits(Model model) {
        //model.addAttribute("fruits", fruitRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/stock/index";
    }
}
