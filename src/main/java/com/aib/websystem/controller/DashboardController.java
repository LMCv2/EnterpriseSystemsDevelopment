package com.aib.websystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.ReserveNeedDTO;
import com.aib.websystem.service.EventService;
import com.aib.websystem.util.TimePeriodConverter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String getDashboardPage(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false, defaultValue = "shop") String groupBy,
            @RequestParam(defaultValue = "1") Integer page,
            Model model) {

        Integer timePeriod = TimePeriodConverter.convertToTimePeriod(new Date());
        Date[] timePeriodRange = TimePeriodConverter.convertToDateRange(timePeriod);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;

        try {
            if (startDate != null && !startDate.isEmpty()) {
                start = dateFormat.parse(startDate);
            } else {
                start = timePeriodRange[0];
            }

            if (endDate != null && !endDate.isEmpty()) {
                end = dateFormat.parse(endDate);
            } else {
                end = timePeriodRange[1];
            }
        } catch (ParseException e) {
            start = timePeriodRange[0];
            end = timePeriodRange[1];
        }

        Page<ReserveNeedDTO> reserveNeeds = eventService.aggregateReserveNeeds(groupBy, start, end, PageRequest.of(page - 1, 10));

        model.addAttribute("timePeriod", timePeriod);
        model.addAttribute("timePeriodRange", timePeriodRange);
        model.addAttribute("reserveNeeds", reserveNeeds);
        return "/pages/dashboard/index";
    }
}
