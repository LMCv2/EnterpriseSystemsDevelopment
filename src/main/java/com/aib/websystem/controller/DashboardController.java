package com.aib.websystem.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aib.websystem.util.TimePeriodConverter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping("/")
    public String getDashboardPage(Model model) {
        Integer timePeriod = TimePeriodConverter.convertToTimePeriod(new Date());
        model.addAttribute("timePeriod", timePeriod);
        model.addAttribute("timePeriodRange", TimePeriodConverter.convertToDateRange(timePeriod));
        return "/pages/dashboard/index";
    }
}
