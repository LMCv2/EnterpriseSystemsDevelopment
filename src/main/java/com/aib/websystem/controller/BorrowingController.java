package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Borrowing;
import com.aib.websystem.repository.BorrowingRepository;

@Controller
@RequestMapping(path = "/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("borrowing", borrowingRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/borrowing/index";
    }

    @GetMapping("/create")
    public String createBorrowing() {
        return "/pages/borrowing/create";
    }
}