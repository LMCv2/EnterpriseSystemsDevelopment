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

import com.aib.websystem.entity.Record;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.RecordRepository;
import com.aib.websystem.repository.StockRepository;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Controller
@RequestMapping(path = "/record")
public class BorrowingController {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("borrowing", recordRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/record/index";
    }

    @GetMapping("/{id}")
    public String createBorrowing(@PathVariable Long id, @PathVariable String city, Model model,
            @Spec(path = "city", spec = Like.class) Specification<Location> spec) {
        model.addAttribute("stock", stockRepository.findById(id).orElse(null));
        model.addAttribute("sameCity", locationRepository.findAll(spec));
        return "/pages/record/new";
    }
}