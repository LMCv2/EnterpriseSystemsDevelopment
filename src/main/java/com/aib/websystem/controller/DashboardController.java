package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @GetMapping("/")
    public String getDashboardPage(Model model) {
        return "/pages/dashboard/index";
    }
}
