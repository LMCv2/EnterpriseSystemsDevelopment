package com.aib.websystem.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum EventStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    REJECTED("Rejected"),
    CONFIRMED("Confirmed"); // for consumption only

    public static Map<String, String> MAP = new LinkedHashMap<>();

    static {
        for (EventStatus value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private EventStatus(String label) {
        this.label = label;
    }
}
