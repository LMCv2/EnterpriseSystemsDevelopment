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
    public String getLocationsPage(@RequestParam(defaultValue = "1") Integer page, Model model) {
        model.addAttribute("locations", locationRepository.findAll(PageRequest.of(page - 1, 10)));
        return "/pages/location/index";
    }

    @GetMapping("/new")
    public String createLocationPage(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("locationType_items", LocationType.MAP);
        return "/pages/location/new";
    }

    @GetMapping("/{id}")
    public String updateLocationPage(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationRepository.findById(id).orElse(null));
        model.addAttribute("locationType_items", LocationType.MAP);
        return "/pages/location/edit";
    }

    @PostMapping("/new")
    public String createLocation(Location location) {
        locationRepository.save(location);
        stockService.addAllFruitToLocation(location);
        return "redirect:/location/";
    }

    @PutMapping("/{id}")
    public String updateLocation(@PathVariable Long id, Location newLocation) {
        if (locationRepository.existsById(id)) {
            Location originLocation = locationRepository.findById(id).get();
            originLocation.setName(newLocation.getName());
            originLocation.setCityName(newLocation.getCityName());
            originLocation.setType(newLocation.getType());
            locationRepository.save(originLocation);
        }
        return "redirect:/location/";
    }

    @DeleteMapping("/{id}")
    public String deleteLocation(@PathVariable Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        }
        return "redirect:/location/";
    }
}
