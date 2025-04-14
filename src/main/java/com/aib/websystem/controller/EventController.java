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
    public String getEventsPage(@RequestParam(defaultValue = "all") String activeTag,
            @RequestParam(defaultValue = "1") Integer page, Model model, @SessionAttribute Account current_account) {
        // model.addAttribute("events", eventRepository.findAll(PageRequest.of(page - 1,
        // 10)));
        switch (activeTag) {
            case "all":
                model.addAttribute("events",
                        eventRepository.findByFromLocationOrToLocation(current_account.getLocation(),
                                current_account.getLocation(), PageRequest.of(page - 1, 10)));
                break;

            case "pending":
            // model.addAttribute("events", eventRepository.findByFromLocationOrToLocation(current_account.getLocation(),
            //         current_account.getLocation(), PageRequest.of(page - 1, 10)));
                if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndFromLocation(EventStatus.PENDING,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                } else if (current_account.getRole() == Role.SHOP_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndToLocation(EventStatus.PENDING,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                }
                break;
            case "shipped":
                if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndFromLocation(EventStatus.SHIPPED,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                } else if (current_account.getRole() == Role.SHOP_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndToLocation(EventStatus.SHIPPED,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                }
                break;
            case "delivered":
                if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndFromLocation(EventStatus.DELIVERED,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                } else if (current_account.getRole() == Role.SHOP_STAFF) {
                    model.addAttribute("events",
                            eventRepository.findByStatusAndToLocation(EventStatus.DELIVERED,
                                    current_account.getLocation(), PageRequest.of(page - 1, 10)));
                }
                break;
        }
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
            case CENTRAL_WAREHOUSE_STAFF:
                Stock stock;
                originEvent.setStatus(EventStatus.DELIVERED);
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
            case SOURCE_WAREHOUSE_STAFF:
                originEvent.setEventType(EventType.RESERVATION);
                originEvent.setStatus(EventStatus.SHIPPED);
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