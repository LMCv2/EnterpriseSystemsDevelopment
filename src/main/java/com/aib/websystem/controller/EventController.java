package com.aib.websystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
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
        model.addAttribute("current_account", current_account);
        model.addAttribute("status_items", EventStatus.MAP);
        if (current_account.getRole() == Role.ADMIN) {
            if (type.equals("event")) {
                if (status.equals("all")) {
                    model.addAttribute("events", eventRepository.findAll(PageRequest.of(page - 1, 10)));
                } else {
                    model.addAttribute("events", eventRepository.findByStatus(EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
                }
                return "/pages/event/index";
            } else if (type.equals("eventgroup")) {
                if (status.equals("all")) {
                    model.addAttribute("events", eventService.findGroupedEvents(PageRequest.of(page - 1, 10)));
                } else {
                    model.addAttribute("events", eventService.findGroupedEventsByStatus(EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
                }
                return "/pages/event/indexGroup";
            }
        } else if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
            if (status.equals("all")) {
                model.addAttribute("events", eventService.findGroupedEventsByFromLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventService.findGroupedEventsByFromLocationAndStatus(current_account.getLocation(), EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
            return "/pages/event/indexGroup";
        } else if (current_account.getRole() == Role.CENTRAL_WAREHOUSE_STAFF) {
            if (status.equals("all")) {
                model.addAttribute("events", eventService.findGroupedEventsByThroughLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventService.findGroupedEventsByThroughLocationAndStatus(current_account.getLocation(), EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
            return "/pages/event/indexGroup";
        } else if (current_account.getRole() == Role.SHOP_STAFF) {
            if (status.equals("all")) {
                model.addAttribute("events", eventRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("events", eventRepository.findByLocationAndStatus(current_account.getLocation(), EventStatus.valueOf(status.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
            return "/pages/event/index";
        }
        return "redirect:/dashboard/";
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

    @PutMapping("/groupApprove")
    public String groupApprove(@RequestParam("events") List<Event> events, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        if (current_account.getRole() == Role.SOURCE_WAREHOUSE_STAFF) {
            Integer totalQuantity = events.stream().mapToInt(Event::getQuantity).sum();
            Stock stock = stockRepository.findByFruitAndLocation(events.get(0).getFruit(), events.get(0).getFromLocation()).get();
            if (stock.getQuantity() >= totalQuantity) {
                stock.setQuantity(stock.getQuantity() - totalQuantity);
                stockRepository.save(stock);
                for (Event event : events) {
                    event.setStatus(EventStatus.SHIPPEDCENTRAL);
                    eventRepository.save(event);
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. The stock is not enough.");
            }
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/groupReject")
    public String groupReject(@RequestParam("events") List<Event> events, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        for (Event event : events) {
            event.setStatus(EventStatus.REJECTED);
            eventRepository.save(event);
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/groupReceive")
    public String groupReceive(@RequestParam("events") List<Event> events, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        if (current_account.getRole() == Role.CENTRAL_WAREHOUSE_STAFF) {
            Integer totalQuantity = events.stream().mapToInt(Event::getQuantity).sum();
            Stock stock = stockRepository.findByFruitAndLocation(events.get(0).getFruit(), events.get(0).getThroughLocation()).get();
            stock.setQuantity(stock.getQuantity() + totalQuantity);
            stockRepository.save(stock);
            for (Event event : events) {
                event.setStatus(EventStatus.DELIVEREDCENTRAL);
                eventRepository.save(event);
            }
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/delivering")
    public String delivering(@RequestParam("event") Event event, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        if (current_account.getRole() == Role.CENTRAL_WAREHOUSE_STAFF) {
            Stock stock = stockRepository.findByFruitAndLocation(event.getFruit(), event.getThroughLocation()).get();
            if (stock.getQuantity() >= event.getQuantity()) {
                stock.setQuantity(stock.getQuantity() - event.getQuantity());
                stockRepository.save(stock);
                event.setStatus(EventStatus.SHIPPED);
                eventRepository.save(event);
            } else {
                redirectAttributes.addFlashAttribute("error", "Error occurred while processing the event. Tthe stock is not enough.");
            }
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/receive")
    public String receive(@RequestParam("event") Event event, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        if (current_account.getRole() == Role.SHOP_STAFF) {
            Stock stock = stockRepository.findByFruitAndLocation(event.getFruit(), event.getToLocation()).get();
            stock.setQuantity(stock.getQuantity() + event.getQuantity());
            stockRepository.save(stock);
            event.setStatus(EventStatus.DELIVERED);
            eventRepository.save(event);
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/approve")
    public String approve(@RequestParam("event") Event event, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        if (current_account.getRole() == Role.SHOP_STAFF) {
            Stock stock = stockRepository.findByFruitAndLocation(event.getFruit(), event.getFromLocation()).get();
            if (stock.getQuantity() >= event.getQuantity()) {
                stock.setQuantity(stock.getQuantity() - event.getQuantity());
                stockRepository.save(stock);
                event.setStatus(EventStatus.SHIPPED);
                eventRepository.save(event);
            } else {
                redirectAttributes.addFlashAttribute("error", (stock.getQuantity() >= event.getQuantity()) + "Error occurred while processing the event. The stock is not enough.");
            }
        }
        return "redirect:" + (referer == null ? "/event/" : referer);
    }

    @PutMapping("/reject")
    public String reject(@RequestParam("event") Event event, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account, RedirectAttributes redirectAttributes) {
        event.setStatus(EventStatus.REJECTED);
        eventRepository.save(event);
        return "redirect:" + (referer == null ? "/event/" : referer);
    }
}
