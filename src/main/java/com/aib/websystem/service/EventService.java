package com.aib.websystem.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.ReserveNeedDTO;
import com.aib.websystem.entity.SeasonalConsumptionDTO;
import com.aib.websystem.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    // grouped events
    public Page<List<Event>> findGroupedEvents(Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinct(pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByStatus(EventStatus status, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByStatus(status, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByDeliveredStatus(Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByDeliveredStatus(pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByFromLocation(Location fromLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByFromLocation(fromLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByFromLocationAndStatus(Location fromLocation, EventStatus status, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByFromLocationAndStatus(fromLocation, status, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByFromLocationAndDeliveredStatus(Location fromLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByFromLocationAndDeliveredStatus(fromLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByThroughLocation(Location throughLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByThroughLocation(throughLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByThroughLocationAndStatus(Location throughLocation, EventStatus status, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByThroughLocationAndStatus(throughLocation, status, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByThroughLocationAndDeliveredStatus(Location throughLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByThroughLocationAndDeliveredStatus(throughLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndTimePeriodAndFromLocationAndThroughLocation((Fruit) group[0], (Integer) group[1], (Location) group[2], (Location) group[3]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    // report
    public Page<ReserveNeedDTO> aggregateReserveNeeds(String groupBy, Date startDate, Date endDate, Pageable pageable) {
        switch (groupBy) {
            case "shop":
            default:
                return eventRepository.findReserveNeedsByShop(startDate, endDate, pageable);
            case "city":
                return eventRepository.findReserveNeedsByCity(startDate, endDate, pageable);
            case "country":
                return eventRepository.findReserveNeedsByCountry(startDate, endDate, pageable);
        }
    }

    // seasonal consumption report
    public Page<SeasonalConsumptionDTO> aggregateSeasonalConsumption(String groupBy, Date startDate, Date endDate, Pageable pageable) {
        switch (groupBy) {
            case "shop":
            default:
                return eventRepository.findSeasonalConsumptionByShop(startDate, endDate, pageable);
            case "city":
                return eventRepository.findSeasonalConsumptionByCity(startDate, endDate, pageable);
            case "country":
                return eventRepository.findSeasonalConsumptionByCountry(startDate, endDate, pageable);
        }
    }
}
