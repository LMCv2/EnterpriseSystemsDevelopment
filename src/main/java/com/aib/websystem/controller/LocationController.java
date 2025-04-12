package com.aib.websystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/location")
public class LocationController {
    @GetMapping("")
    public String getLocationPage() {
        return "/pages/location/index";
    }
}
