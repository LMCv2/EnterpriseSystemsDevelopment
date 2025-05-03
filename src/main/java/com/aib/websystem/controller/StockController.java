package com.aib.websystem.controller;

import java.util.Date;

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
import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.EventType;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.StockRepository;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/")
    public String getStocksPage(@RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        if (current_account.getRole() == Role.ADMIN) {
            model.addAttribute("stocks", stockRepository.findAll(PageRequest.of(page - 1, 10)));
        } else {
            model.addAttribute("stocks", stockRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        }
        return "/pages/stock/index";
    }

    @GetMapping("/{id}/add")
    public String addStockPage(@RequestParam(defaultValue = "reservation") String type, @PathVariable Long id, Model model) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (type.equals("reservation")) {
            model.addAttribute("stocks", stockRepository.findByFruitAndLocationType(stock.getFruit(), LocationType.SOURCE_WAREHOUSE, PageRequest.of(0, 10)));
        } else if (type.equals("borrowing")) {
            model.addAttribute("stocks", stockRepository.findByFruitAndLocationTypeAndLocationCityNameAndIdNot(stock.getFruit(), LocationType.SHOP, stock.getLocation().getCityName(), stock.getId(), PageRequest.of(0, 10)));
        }
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

    @PostMapping("/reserve/{id}")
    public String reserve(@PathVariable Long id, @RequestParam int reservation_number, @SessionAttribute Account current_account) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stockRepository.existsById(id)) {
            eventRepository.save(new Event(stock.getFruit(), reservation_number, EventType.RESERVATION, stock.getLocation(), current_account.getLocation(), new Date(), EventStatus.PENDING));
        }
        return "redirect:/stock/";
    }
}
