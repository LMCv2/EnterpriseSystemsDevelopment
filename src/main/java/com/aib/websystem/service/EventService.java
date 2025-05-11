package com.aib.websystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Page<List<Event>> findGroupedEvents(Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinct(pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndFromLocationAndThroughLocation((Fruit) group[0], (Location) group[1], (Location) group[2]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByFromLocation(Location fromLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByFromLocation(fromLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndFromLocationAndThroughLocation((Fruit) group[0], (Location) group[1], (Location) group[2]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }

    public Page<List<Event>> findGroupedEventsByThroughLocation(Location throughLocation, Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctByThroughLocation(throughLocation, pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> eventRepository.findByFruitAndFromLocationAndThroughLocation((Fruit) group[0], (Location) group[1], (Location) group[2]))
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }
}
