package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.Role;
import com.aib.websystem.repository.LocationRepository;

@Controller
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String getLocationsPage(Model model) {
        model.addAttribute("locations", locationRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/location/index";
    }

    @GetMapping("/new")
    public String createLocationPage(Model model) {
        model.addAttribute("location", new Location());
        return "/pages/location/new";
    }
}
