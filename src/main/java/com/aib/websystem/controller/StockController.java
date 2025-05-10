package com.aib.websystem.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.ReservationSchedule;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.ReservationScheduleRepository;
import com.aib.websystem.repository.StockRepository;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReservationScheduleRepository reservationScheduleRepository;

    @GetMapping("/")
    public String getStocksPage(@RequestParam(defaultValue = "all") String type, @RequestParam(defaultValue = "1") Integer page, @SessionAttribute Account current_account, Model model) {
        if (current_account.getRole() == Role.ADMIN) {
            if (type.equals("all")) {
                model.addAttribute("stocks", stockRepository.findAll(PageRequest.of(page - 1, 10)));
            } else {
                model.addAttribute("stocks", stockRepository.findByLocationType(LocationType.valueOf(type.toUpperCase()), PageRequest.of(page - 1, 10)));
            }
            model.addAttribute("locationType_items", LocationType.MAP);
        } else {
            model.addAttribute("stocks", stockRepository.findByLocation(current_account.getLocation(), PageRequest.of(page - 1, 10)));
        }
        model.addAttribute("current_account", current_account);
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

    @GetMapping("/{id}/addReservedNeeds")
    public String addReservedNeeds(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer qty, @SessionAttribute Account current_account, Model model) {
        Fruit fruit = stockRepository.findById(id).orElse(null).getFruit();
        Stock stock = stockRepository.findByFruitAndLocation(fruit, current_account.getLocation()).get();
        stock.setQuantity(stock.getQuantity() + qty);
        stockRepository.save(stock);
        return "redirect:/stock/";
        //return "redirect:/stock/totalReservedNeedsOverall";
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

    @GetMapping("/{toId}/from/{fromId}")
    public String addStockFromPage(@PathVariable Long fromId, @PathVariable Long toId, Model model) {
        model.addAttribute("from_stocks", stockRepository.findById(fromId).orElse(null));
        model.addAttribute("to_stocks", stockRepository.findById(toId).orElse(null));
        return "/pages/stock/addfrom";
    }

    @GetMapping("/{id}/consume")
    public String removeStockPage(@PathVariable Long id, Model model) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        return "/pages/stock/consume";
    }

    @PostMapping("/{toId}/from/{fromId}")
    public String createEvent(@PathVariable Long fromId, @PathVariable Long toId, @RequestParam int quantity, @SessionAttribute Account current_account) {
        Stock fromStock = stockRepository.findById(fromId).orElse(null);
        Stock toStack = stockRepository.findById(toId).orElse(null);
        EventType eventType = fromStock.getLocation().getType() == LocationType.SHOP ? EventType.BORROWING : EventType.RESERVATION;
        if (stockRepository.existsById(fromId)) {
            if(eventType == EventType.RESERVATION) {
                // be careful, each city MUST has his own central warehouse(only one)
                Page<Location> centralWarehouse = locationRepository.findByCityNameAndType(toStack.getLocation().getCityName(), LocationType.CENTRAL_WAREHOUSE, PageRequest.of(0, 10));
                Iterable<ReservationSchedule> reservationSchedule = reservationScheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
                eventRepository.save(new Event(fromStock.getFruit(), quantity, eventType, fromStock.getLocation(), centralWarehouse.getContent().get(0), fromStock.getLocation(), toStack.getLocation(), new Date(), EventStatus.PENDING, reservationSchedule.iterator().hasNext() ? reservationSchedule.iterator().next() : null));
            } else {
                eventRepository.save(new Event(fromStock.getFruit(), quantity, eventType, fromStock.getLocation(), toStack.getLocation(), fromStock.getLocation(), toStack.getLocation(), new Date(), EventStatus.PENDING));
            }
        }
        return "redirect:/stock/";
    }

    @PostMapping("/update/{id}")
    public String test(@PathVariable Long id, @RequestParam int quantity) {
        if (stockRepository.existsById(id)) {
            Stock stock = stockRepository.findById(id).get();
            stock.setQuantity(stock.getQuantity() - quantity);
            stockRepository.save(stock);
        }
        return "redirect:/stock/";
    }
}
