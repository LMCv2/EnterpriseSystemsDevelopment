package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.repository.StockRepository;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String getEventsPage(Model model) {
        model.addAttribute("events", eventRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/event/index";
    }

    @GetMapping("/{id}")
    public String createBorrowing(@PathVariable Long id, @PathVariable String city, Model model,
            @Spec(path = "city", spec = Like.class) Specification<Location> spec) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        model.addAttribute("sameCity", locationRepository.findAll(spec));
        return "/pages/event/new";
    }
}