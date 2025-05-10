package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.EventType;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.StockRepository;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public String getEventsPage(@RequestParam(defaultValue = "all") String status, @RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        if (current_account.getRole() == Role.ADMIN) {
            if (status.equals("all")) {
                model.addAttribute("events", eventRepository.findAll(PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventRepository.findByStatus(EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
        } else {
            if (status.equals("all")) {
                model.addAttribute("events", eventRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventRepository.findByLocationAndStatus(current_account.getLocation(), EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
        }
        model.addAttribute("status_items", EventStatus.MAP);
        model.addAttribute("current_account", current_account);
        return "/pages/event/index";
    }

    @GetMapping("/new")
    public String getNewEventPage() {
        return "/pages/event/new";
    }

    @GetMapping("/{id}")
    public String getEventPage(@PathVariable Long id, @SessionAttribute Account current_account) {
        Event originEvent = eventRepository.findById(id).orElse(null);
        switch (current_account.getRole()) {
            case ADMIN:
                break;
            case SHOP_STAFF:
                Stock stock;
                originEvent.setStatus(EventStatus.CONFIRMED);
                Page<Stock> stocks = stockRepository.findByFruitAndLocationType(originEvent.getFruit(),
                        originEvent.getToLocation().getType(), null);
                if (stocks.isEmpty()) {
                    stock = new Stock(originEvent.getFruit(), originEvent.getToLocation(), originEvent.getQuantity());
                } else {
                    stock = stocks.getContent().get(0);
                    stock.setQuantity(stock.getQuantity() + originEvent.getQuantity());
                }
                stockRepository.save(stock);
                break;
            case CENTRAL_WAREHOUSE_STAFF:
                Stock stock2;
                originEvent.setFromLocation(originEvent.getToLocation());
                originEvent.setToLocation(originEvent.getFinalToLocation());
                Page<Stock> stocks2 = stockRepository.findByFruitAndLocation(originEvent.getFruit(),
                        originEvent.getFromLocation(), null);
                stock2 = stocks2.getContent().get(0);
                originEvent.setStatus(EventStatus.DELIVERED);
                stock2.setQuantity(stock2.getQuantity() - originEvent.getQuantity());

                stockRepository.save(stock2);
                break;
            case SOURCE_WAREHOUSE_STAFF:
                Stock stock3;
                Page<Stock> stocks3 = stockRepository.findByFruitAndLocation(originEvent.getFruit(),
                        current_account.getLocation(), null);
                stock3 = stocks3.getContent().get(0);
                if (stock3.getQuantity() >= originEvent.getQuantity()) {
                    originEvent.setEventType(EventType.RESERVATION);
                    originEvent.setStatus(EventStatus.SHIPPED);
                    stock3.setQuantity(stock3.getQuantity() - originEvent.getQuantity());
                } else {

                }
                stockRepository.save(stock3);
                break;
            case SENIOR_MANAGEMENT:
                break;
        }
        eventRepository.save(originEvent);
        return "redirect:/event/";
    }

    // @GetMapping("/{id}")
    // public String createBorrowing(@PathVariable Long id, @PathVariable String
    // city, Model model,
    // @Spec(path = "city", spec = Like.class) Specification<Location> spec) {
    // model.addAttribute("stock", stockRepository.findById(id).orElse(null));
    // model.addAttribute("sameCity", locationRepository.findAll(spec));
    // return "/pages/event/new";
    // }
}
