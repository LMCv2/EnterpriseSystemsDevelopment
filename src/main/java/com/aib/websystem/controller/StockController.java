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
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.StockRepository;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public String getStocksPage(
            @RequestParam(defaultValue = "1") Integer page,
            @SessionAttribute Account current_account,
            Model model) {
        if (current_account.getLocation() == null) {
            model.addAttribute("stocks", stockRepository.findAll(PageRequest.of(page - 1, 10)));
        } else {
            model.addAttribute("stocks", stockRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        }
        return "/pages/stock/index";
    }

    @GetMapping("/{id}/add")
    public String addStockPage(@PathVariable Long id, Model model) {
        Stock stock = stockRepository.findById(id).orElse(null);
        model.addAttribute("stocks", stockRepository.findByFruitAndLocationTypeNot(stock.getFruit(),
                LocationType.CENTRAL_WAREHOUSE, PageRequest.of(0, 10)));

        // Specification<Long> spec = (root, query, criteriaBuilder) ->
        // criteriaBuilder.equal(root.get("fruit").get("id"), id);
        // model.addAttribute("stocks", stockRepository.findAll(spec, PageRequest.of(0,
        // 10)));
        return "/pages/stock/add";
    }

    @GetMapping("/{id}/remove")
    public String removeStockPage(@PathVariable Long id, Model model) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        return "/pages/stock/remove";
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
