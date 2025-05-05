package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aib.websystem.repository.ReservationScheduleRepository;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private ReservationScheduleRepository reservationScheduleRepository;
    
    @GetMapping("/")
    public String getDashboardPage(Model model) {
        model.addAttribute("reservation_schedules", reservationScheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "/pages/dashboard/index";
    }
}
