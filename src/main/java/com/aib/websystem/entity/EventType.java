package com.aib.websystem.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum EventType {
    CONSUMPTION("Consumption"),
    RESERVATION("Reservation"),
    BORROWING("Borrowing");

    public static Map<String, String> MAP = new LinkedHashMap<>();

    static {
        for (EventType value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private EventType(String label) {
        this.label = label;
    }
}
