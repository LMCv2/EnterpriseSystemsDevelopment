package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.service.StockService;

@Controller
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public String getLocationsPage(@RequestParam(defaultValue = "all") String type, @RequestParam(defaultValue = "1") Integer page, Model model) {
        if (type.equals("all")) {
            model.addAttribute("locations", locationRepository.findByDeletedFalse(PageRequest.of(page - 1, 10)));
        } else if (type.equals("deleted")) {
            model.addAttribute("locations", locationRepository.findByDeletedTrue(PageRequest.of(page - 1, 10)));
        } else {
            model.addAttribute("locations", locationRepository.findByTypeAndDeletedFalse(LocationType.valueOf(type.toUpperCase()), PageRequest.of(page - 1, 10)));
        }
        model.addAttribute("locationType_items", LocationType.MAP);
        return "/pages/location/index";
    }

    @GetMapping("/create")
    public String createLocationPage(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("locationType_items", LocationType.MAP);
        return "/pages/location/create";
    }

    @GetMapping("/{id}/edit")
    public String editLocationPage(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationRepository.findById(id).orElse(null));
        model.addAttribute("locationType_items", LocationType.MAP);
        return "/pages/location/edit";
    }

    @PostMapping("/create")
    public String createLocation(Location location) {
        locationRepository.save(location);
        stockService.addAllFruitToLocation(location);
        return "redirect:/location/";
    }

    @PutMapping("/{id}/edit")
    public String editLocation(@PathVariable Long id, Location newLocation) {
        if (locationRepository.existsById(id)) {
            Location originLocation = locationRepository.findById(id).get();
            originLocation.setName(newLocation.getName());
            originLocation.setCity(newLocation.getCity());
            originLocation.setType(newLocation.getType());
            originLocation.setDeleted(newLocation.getDeleted());
            locationRepository.save(originLocation);
        }
        return "redirect:/location/";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteLocation(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            Location location = locationRepository.findById(id).get();
            location.setDeleted(true);
            locationRepository.save(location);
        }
        return "redirect:/location/";
    }
}
