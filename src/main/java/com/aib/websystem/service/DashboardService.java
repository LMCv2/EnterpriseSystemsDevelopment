package com.aib.websystem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.DeliveryForecastDTO;
import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.EventType;
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

    public Page<DeliveryForecastDTO> calculateDeliveryForecasts(Date startDate, Date endDate, Pageable pageable) {
        List<Event> completedEvents = eventRepository.findByEventTypeAndStatusAndCreateDateBetween(
                EventType.RESERVATION,
                EventStatus.DELIVERED,
                startDate,
                endDate);

        Map<String, List<Event>> groupedEvents = completedEvents.stream()
                .collect(Collectors.groupingBy(event -> event.getFruit().getId() + "_" +
                        event.getFromLocation().getId() + "_" +
                        event.getToLocation().getId()));

        List<DeliveryForecastDTO> forecasts = new ArrayList<>();

        for (List<Event> events : groupedEvents.values()) {
            if (!events.isEmpty()) {
                Event sampleEvent = events.get(0);

                double averageDeliveryDays = events.stream()
                        .mapToLong(event -> TimeUnit.MILLISECONDS.toDays(
                                event.getLastModifiedDate().getTime() - event.getCreateDate().getTime()))
                        .average()
                        .orElse(0.0);

                double mean = averageDeliveryDays;
                double standardDeviation = Math.sqrt(
                        events.stream()
                                .mapToDouble(event -> {
                                    long days = TimeUnit.MILLISECONDS.toDays(
                                            event.getLastModifiedDate().getTime() - event.getCreateDate().getTime());
                                    return Math.pow(days - mean, 2);
                                })
                                .average()
                                .orElse(0.0));

                forecasts.add(new DeliveryForecastDTO(
                        sampleEvent.getFruit(),
                        sampleEvent.getFromLocation(),
                        sampleEvent.getToLocation(),
                        averageDeliveryDays,
                        standardDeviation));
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), forecasts.size());

        return new PageImpl<>(
                forecasts.subList(start, end),
                pageable,
                forecasts.size());
    }

    // chart
    public Map<String, Long> getFruitReservationDistribution(Date startDate, Date endDate) {
        return eventRepository.findByCreateDateBetweenAndEventType(
                startDate,
                endDate,
                EventType.RESERVATION).stream()
                .collect(Collectors.groupingBy(
                        event -> event.getFruit().getName(),
                        Collectors.summingLong(Event::getQuantity)));
    }

    public Map<String, Long> getFruitBorrowingDistribution(Date startDate, Date endDate) {
        return eventRepository.findByCreateDateBetweenAndEventType(
                startDate,
                endDate,
                EventType.BORROWING).stream()
                .collect(Collectors.groupingBy(
                        event -> event.getFruit().getName(),
                        Collectors.summingLong(Event::getQuantity)));
    }
}
