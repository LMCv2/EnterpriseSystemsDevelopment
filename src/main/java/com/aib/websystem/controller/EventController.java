package com.aib.websystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{id}")
    public String getEventPage(@PathVariable Long id, @RequestParam(defaultValue = "confirm") String action, @SessionAttribute Account current_account, Model model, RedirectAttributes redirectAttributes) {
        Event originEvent = eventRepository.findById(id).orElse(null);
        if (action.equals("confirm")) {
            switch (current_account.getRole()) {
                case ADMIN:
                    break;
                case SHOP_STAFF:
                    if (originEvent.getEventType() == EventType.RESERVATION || (originEvent.getEventType() == EventType.BORROWING && originEvent.getStatus() == EventStatus.DELIVERED)) {
                        originEvent.setStatus(EventStatus.CONFIRMED);
                        Optional<Stock> stocks = stockRepository.findByFruitAndLocation(originEvent.getFruit(), originEvent.getToLocation());
                        Stock stock = stocks.get();
                        if (stocks.isEmpty()) {
                            stock = new Stock(originEvent.getFruit(), originEvent.getToLocation(), originEvent.getQuantity());
                        } else {
                            stock.setQuantity(stock.getQuantity() + originEvent.getQuantity());
                        }
                        stockRepository.save(stock);
                    } else {
                        Optional<Stock> stocks = stockRepository.findByFruitAndLocation(originEvent.getFruit(), originEvent.getFromLocation());
                        Stock stock = stocks.get();
                        if (stock.getQuantity() >= originEvent.getQuantity()) {
                            originEvent.setStatus(EventStatus.DELIVERED);
                            stock.setQuantity(stock.getQuantity() - originEvent.getQuantity());
                        } else {
                            redirectAttributes.addFlashAttribute("error", (stock.getQuantity() >= originEvent.getQuantity()) + "Error occurred while processing the event. The stock is not enough.");
                        }
                    }
                    break;
                case CENTRAL_WAREHOUSE_STAFF:
                    Stock stock2 = stockRepository.findByFruitAndLocation(originEvent.getFruit(), originEvent.getToLocation()).get();
                    if (stock2.getQuantity() >= originEvent.getQuantity()) {
                        originEvent.setStatus(EventStatus.DELIVERED);
                        originEvent.setFromLocation(originEvent.getToLocation());
                        originEvent.setToLocation(originEvent.getFinalToLocation());
                        stock2.setQuantity(stock2.getQuantity() - originEvent.getQuantity());
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. Tthe stock is not enough.");
                    }
                    stockRepository.save(stock2);
                    break;
                case SOURCE_WAREHOUSE_STAFF:
                    Stock stock3 = stockRepository.findByFruitAndLocation(originEvent.getFruit(), current_account.getLocation()).get();
                    if (stock3.getQuantity() >= originEvent.getQuantity()) {
                        originEvent.setStatus(EventStatus.SHIPPED);
                        stock3.setQuantity(stock3.getQuantity() - originEvent.getQuantity());
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. The stock is not enough.");
                    }
                    stockRepository.save(stock3);
                    break;
                case SENIOR_MANAGEMENT:
                    break;
            }
        } else {
            originEvent.setStatus(EventStatus.REJECTED);
        }
        eventRepository.save(originEvent);
        return "redirect:/event/";
    }
}
