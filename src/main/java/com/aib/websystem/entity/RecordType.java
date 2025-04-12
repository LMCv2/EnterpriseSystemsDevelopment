package com.aib.websystem.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum RecordType {
    CONSUPTION("Consumption"),
    RESERVATION("Reservation"),
    BORROWING("Borrowing");

    public static Map<String, String> MAP = new LinkedHashMap<>();

    static {
        for (RecordType value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private RecordType(String label) {
        this.label = label;
    }
}
