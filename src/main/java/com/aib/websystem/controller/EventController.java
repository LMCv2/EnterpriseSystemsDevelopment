package com.aib.websystem.controller;

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
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.StockRepository;
import com.aib.websystem.service.EventService;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public String getEventsPage(@RequestParam(defaultValue = "event") String type, @RequestParam(defaultValue = "all") String status, @RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        model.addAttribute("status_items", EventStatus.MAP);
        model.addAttribute("current_account", current_account);
        if (current_account.getRole() == Role.ADMIN) {
            if (type.equals("event")) {
                if (status.equals("all")) {
                    model.addAttribute("events", eventRepository.findAll(PageRequest.of(page - 1, 10)));
                } else {
                    model.addAttribute("events", eventRepository.findByStatus(EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
                }
            } else if (type.equals("eventgroup")) {
                if (status.equals("all")) {
                    model.addAttribute("events", eventService.findGroupedEvents(PageRequest.of(page - 1, 10)));
                } else {
                    model.addAttribute("events", eventService.findGroupedEventsByStatus(EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
                }
                return "/pages/event/indexGroup";
            }
            return "/pages/event/index";
        } else if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
            model.addAttribute("events", eventService.findGroupedEventsByFromLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            return "/pages/event/indexGroup";
        } else if (current_account.getRole() == Role.CENTRAL_WAREHOUSE_STAFF) {
            model.addAttribute("events", eventService.findGroupedEventsByThroughLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            return "/pages/event/indexGroup";
        } else {
            if (status.equals("all")) {
                model.addAttribute("events", eventRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventRepository.findByLocationAndStatus(current_account.getLocation(), EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
            return "/pages/event/index";
        }
    }

    //
    @GetMapping("/{id}/addReservedNeeds")
    public String addReservedNeeds(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer qty, @SessionAttribute Account current_account, Model model) {
        Fruit fruit = stockRepository.findById(id).orElse(null).getFruit();
        Stock stock = stockRepository.findByFruitAndLocation(fruit, current_account.getLocation()).get();
        stock.setQuantity(stock.getQuantity() + qty);
        stockRepository.save(stock);
        return "redirect:/stock/";
    }

    @GetMapping("/totalReservedNeedsOverall")
    public String getTotalReservedNeedsPage(@RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        model.addAttribute("selectionFruitList", eventRepository.findFruitStockAndEventTotalByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        return "/pages/stock/totalReservedNeedsOverall";
    }

    @GetMapping("/warehousing")
    public String warehousing(@RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        model.addAttribute("warehousingList", eventRepository.findFruitStockAndActiveEventTotalByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        return "/pages/stock/warehousing";
    }

    @GetMapping("/detail")
    public String getTotalReservedNeedsDetailPage(@RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        model.addAttribute("stocksNeeds", eventRepository.findByLocationGroupByFruit(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        return "/pages/stock/totalReservedNeeds";
    }
    //

    @GetMapping("/{id}")
    public String getEventPage(@PathVariable Long id, @RequestParam(defaultValue = "confirm") String action, @SessionAttribute Account current_account, Model model, RedirectAttributes redirectAttributes) {
        Event event = eventRepository.findById(id).get();
        if (action.equals("confirm")) {
            switch (current_account.getRole()) {
                case ADMIN:
                    break;
                case SENIOR_MANAGEMENT:
                    break;
                case SOURCE_WAREHOUSE_STAFF:
                    Stock stock3 = stockRepository.findByFruitAndLocation(event.getFruit(), event.getFromLocation()).get();
                    if (stock3.getQuantity() >= event.getQuantity()) {
                        event.setStatus(EventStatus.SHIPPEDCENTRAL);
                        stock3.setQuantity(stock3.getQuantity() - event.getQuantity());
                        stockRepository.save(stock3);
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. The stock is not enough.");
                    }
                    break;
                case CENTRAL_WAREHOUSE_STAFF:
                    Stock stock2 = stockRepository.findByFruitAndLocation(event.getFruit(), event.getThroughLocation()).get();
                    if (event.getStatus() == EventStatus.SHIPPEDCENTRAL) {
                        event.setStatus(EventStatus.DELIVEREDCENTRAL);
                        stock2.setQuantity(stock2.getQuantity() + event.getQuantity());
                        stockRepository.save(stock2);
                    } else if (event.getStatus() == EventStatus.DELIVEREDCENTRAL) {
                        if (stock2.getQuantity() >= event.getQuantity()) {
                            event.setStatus(EventStatus.SHIPPED);
                            stock2.setQuantity(stock2.getQuantity() - event.getQuantity());
                            stockRepository.save(stock2);
                        } else {
                            redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. Tthe stock is not enough.");
                        }
                    }
                    break;
                case SHOP_STAFF:
                    if (event.getStatus() == EventStatus.SHIPPED) {
                        event.setStatus(EventStatus.DELIVERED);
                        Stock stock = stockRepository.findByFruitAndLocation(event.getFruit(), event.getToLocation()).get();
                        stock.setQuantity(stock.getQuantity() + event.getQuantity());
                        stockRepository.save(stock);
                    } else {
                        Stock stock = stockRepository.findByFruitAndLocation(event.getFruit(), event.getFromLocation()).get();
                        if (stock.getQuantity() >= event.getQuantity()) {
                            event.setStatus(EventStatus.SHIPPED);
                            stock.setQuantity(stock.getQuantity() - event.getQuantity());
                            stockRepository.save(stock);
                        } else {
                            redirectAttributes.addFlashAttribute("error", (stock.getQuantity() >= event.getQuantity()) + "Error occurred while processing the event. The stock is not enough.");
                        }
                    }
                    break;
            }
        } else {
            event.setStatus(EventStatus.REJECTED);
        }
        eventRepository.save(event);
        return "redirect:/event/";
    }
}
