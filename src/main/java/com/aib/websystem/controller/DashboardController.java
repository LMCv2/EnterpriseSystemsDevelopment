package com.aib.websystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.ReserveNeedDTO;
import com.aib.websystem.entity.SeasonalConsumptionDTO;
import com.aib.websystem.entity.DeliveryForecastDTO;
import com.aib.websystem.service.DashboardService;
import com.aib.websystem.service.EventService;
import com.aib.websystem.util.TimePeriodConverter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private EventService eventService;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/")
    public String getDashboardPage(
            @RequestParam(required = false) String startDateString,
            @RequestParam(required = false) String endDateString,
            @RequestParam(required = false, defaultValue = "shop") String groupBy,
            @RequestParam(defaultValue = "reserveNeeds") String type,
            @RequestParam(defaultValue = "1") Integer page,
            Model model) {

        Integer timePeriod = TimePeriodConverter.convertToTimePeriod(new Date());
        Date[] timePeriodRange = TimePeriodConverter.convertToDateRange(timePeriod);
        model.addAttribute("timePeriod", timePeriod);
        model.addAttribute("timePeriodRange", timePeriodRange);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (startDateString == null) {
            startDateString = dateFormat.format(timePeriodRange[0]);
        }
        if (endDateString == null) {
            endDateString = dateFormat.format(timePeriodRange[1]);
        }
        model.addAttribute("startDate", startDateString);
        model.addAttribute("endDate", endDateString);

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startDateString);
        } catch (ParseException e) {
            startDate = timePeriodRange[0];
        }
        try {
            endDate = dateFormat.parse(endDateString);
        } catch (ParseException e) {
            endDate = timePeriodRange[1];
        }

        if (type.equals("reserveNeeds")) {
            Page<ReserveNeedDTO> reserveNeeds = eventService.aggregateReserveNeeds(groupBy, startDate, endDate, PageRequest.of(page - 1, 10));
            model.addAttribute("reserveNeeds", reserveNeeds);
        }
        if (type.equals("seasonalConsumption")) {
            Page<SeasonalConsumptionDTO> seasonalConsumption = eventService.aggregateSeasonalConsumption(groupBy, startDate, endDate, PageRequest.of(page - 1, 10));
            model.addAttribute("seasonalConsumption", seasonalConsumption);
        }
        if (type.equals("deliveryForecast")) {
            Page<DeliveryForecastDTO> forecasts = dashboardService.calculateDeliveryForecasts(startDate, endDate, PageRequest.of(page - 1, 10));
            model.addAttribute("deliveryForecasts", forecasts);
        }

        // cards
        List<Long> dailyEventCounts = dashboardService.getDailyEventCounts(startDate, endDate);
        model.addAttribute("dailyEventCounts", dailyEventCounts);

        List<Long> dailyTotalQuantities = dashboardService.getDailyTotalQuantities(startDate, endDate);
        model.addAttribute("dailyTotalQuantities", dailyTotalQuantities);

        Long pendingEventsCount = dashboardService.getPendingEventsCount();
        model.addAttribute("pendingEventsCount", pendingEventsCount);

        // chart
        Map<String, Long> fruitReservationDistribution = dashboardService.getFruitReservationDistribution(startDate, endDate);
        model.addAttribute("fruitReservationLabels", "[" + fruitReservationDistribution.keySet().stream().map(item -> "\"" + item + "\"").collect(Collectors.joining(",")) + "]");
        model.addAttribute("fruitReservationData", "[" + fruitReservationDistribution.values().stream().map(item -> "\"" + item + "\"").collect(Collectors.joining(",")) + "]");

        Map<String, Long> fruitBorrowingDistribution = dashboardService.getFruitBorrowingDistribution(startDate, endDate);
        model.addAttribute("fruitBorrowingLabels", "[" + fruitBorrowingDistribution.keySet().stream().map(item -> "\"" + item + "\"").collect(Collectors.joining(",")) + "]");
        model.addAttribute("fruitBorrowingData", "[" + fruitBorrowingDistribution.values().stream().map(item -> "\"" + item + "\"").collect(Collectors.joining(",")) + "]");

        return "/pages/dashboard/index";
    }
}
