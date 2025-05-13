package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.StockRepository;
import com.aib.websystem.util.TimePeriodConverter;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationRepository locationRepository;

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

    @GetMapping("/{id}/replenish")
    public String replenishStockPage(@RequestParam(defaultValue = "reservation") String type, @PathVariable Long id, Model model) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (type.equals("reservation")) {
            model.addAttribute("stocks", stockRepository.findByFruitAndLocationType(stock.getFruit(), LocationType.SOURCE_WAREHOUSE, PageRequest.of(0, 10)));
        } else if (type.equals("borrowing")) {
            model.addAttribute("stocks", stockRepository.findByFruitAndLocationTypeAndLocationCityAndIdNot(stock.getFruit(), LocationType.SHOP, stock.getLocation().getCity(), stock.getId(), PageRequest.of(0, 10)));
        }
        return "/pages/stock/replenish";
    }

    @GetMapping("/{toId}/from/{fromId}")
    public String replenishStockFromPage(@PathVariable Long fromId, @PathVariable Long toId, Model model) {
        model.addAttribute("from_stocks", stockRepository.findById(fromId).orElse(null));
        model.addAttribute("to_stocks", stockRepository.findById(toId).orElse(null));
        return "/pages/stock/addfrom";
    }

    @GetMapping("/{id}/edit")
    public String editStockPage(@PathVariable Long id, Model model) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        return "/pages/stock/edit";
    }

    @PostMapping("/{toId}/from/{fromId}")
    public String createReplenishEvent(@PathVariable Long fromId, @PathVariable Long toId, @RequestParam Long quantity, @RequestHeader(value = "Referer") String referer, @SessionAttribute Account current_account,
            RedirectAttributes redirectAttributes) {
        Stock fromStock = stockRepository.findById(fromId).orElse(null);
        Stock toStack = stockRepository.findById(toId).orElse(null);
        Fruit fruit = fromStock.getFruit();
        Location fromLocation = fromStock.getLocation();
        Location toLocation = toStack.getLocation();
        if (stockRepository.existsById(fromId)) {
            if (fromStock.getLocation().getType() == LocationType.SOURCE_WAREHOUSE) {
                // be careful, each city MUST has his own central warehouse(only one)
                Page<Location> centralWarehouse = locationRepository.findByCountryAndCityAndType(toStack.getLocation().getCountry(), toStack.getLocation().getCity(), LocationType.CENTRAL_WAREHOUSE, PageRequest.of(0, 10));

                List<Event> shippedEvents = eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocationAndStatusNot(
                        fruit,
                        TimePeriodConverter.convertToTimePeriod(new Date()),
                        fromLocation,
                        centralWarehouse.getContent().get(0),
                        EventStatus.PENDING);
                if (shippedEvents.isEmpty()) {
                    eventRepository.save(new Event(fruit, quantity, fromLocation, centralWarehouse.getContent().get(0), toLocation));
                } else {
                    redirectAttributes.addFlashAttribute("error", "This reservation has been dispatched, please come back for the next reservation!");
                    return "redirect:" + (referer == null ? "/stock/" : referer);
                }
            } else if (fromStock.getLocation().getType() == LocationType.SHOP) {
                eventRepository.save(new Event(fruit, quantity, fromLocation, toLocation));
            }
        }
        return "redirect:/stock/";
    }

    @PutMapping("/{id}/edit")
    public String editStock(@PathVariable Long id, @RequestParam Long quantity) {
        if (stockRepository.existsById(id)) {
            Stock stock = stockRepository.findById(id).get();
            Long oldQuantity = stock.getQuantity();
            stock.setQuantity(quantity);
            stockRepository.save(stock);

            if (stock.getLocation().getType() == LocationType.SHOP && quantity < oldQuantity) {
                Event consumptionEvent = new Event(stock.getFruit(), oldQuantity - quantity, stock.getLocation());
                eventRepository.save(consumptionEvent);
            }
        }
        return "redirect:/stock/";
    }
}
