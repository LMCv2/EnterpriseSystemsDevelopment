package com.aib.websystem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.repository.EventRepository;

@Service
public class DashboardService {

    @Autowired
    private EventRepository eventRepository;

    // card 1
    public List<Long> getDailyEventCounts(Date startDate, Date endDate) {
        List<Long> dailyCounts = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (!cal.getTime().after(endDate)) {
            Date dayStart = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date dayEnd = cal.getTime();

            Long count = eventRepository.countByCreateDateBetween(dayStart, dayEnd);
            dailyCounts.add(count);
        }
        return dailyCounts;
    }

    // card 2
    public List<Long> getDailyTotalQuantities(Date startDate, Date endDate) {
        List<Long> dailyTotals = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (!cal.getTime().after(endDate)) {
            Date dayStart = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date dayEnd = cal.getTime();

            Long total = eventRepository.sumQuantityByCreateDateBetween(dayStart, dayEnd);
            dailyTotals.add(total != null ? total : 0L);
        }
        return dailyTotals;
    }

    // card 3
    public Long getPendingEventsCount() {
        return eventRepository.countByStatusNotIn(List.of(EventStatus.DELIVERED, EventStatus.REJECTED, EventStatus.CONFIRMED));
    }
}