package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.StockRepository;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public String getStocksPage(@SessionAttribute Account current_account, Model model) {
        if (current_account.getLocation() == null) {
            model.addAttribute("stocks", stockRepository.findAll(PageRequest.of(0, 10)));
        } else {
            model.addAttribute("stocks", stockRepository.findByLocation(current_account.getLocation(), PageRequest.of(0, 10)));
        }
        return "/pages/stock/index";
    }

    @GetMapping("/{id}")
    public String getAccount(@PathVariable Long id, Model model) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        return "/pages/stock/update";
    }

    @PostMapping("/update/{id}")
    public String test(@PathVariable Long id, @RequestParam int quantity) {
        if (stockRepository.existsById(id)) {
            Stock stock = stockRepository.findById(id).get();
            stock.setQuantity(quantity);
            stockRepository.save(stock);
        }
        return "redirect:/stock/";
    }

}