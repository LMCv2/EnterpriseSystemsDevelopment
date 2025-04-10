package com.aib.websystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/dashboard")
public class DashboardController {
    @GetMapping("")
    public String getFruits() {
        return "/pages/dashboard/index";
    }
}
