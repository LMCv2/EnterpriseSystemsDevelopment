package com.aib.websystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Page<List<Event>> getGroupedEvents(Pageable pageable) {
        Page<Object[]> distinctGroups = eventRepository.findDistinctStatusAndLocation(pageable);
        List<List<Event>> groupedEvents = distinctGroups.getContent().stream()
                .map(group -> {
                    EventStatus status = (EventStatus) group[0];
                    Location throughLocation = (Location) group[1];
                    return eventRepository.findByStatusAndThroughLocation(status, throughLocation);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(groupedEvents, pageable, distinctGroups.getTotalElements());
    }
}
