package com.aib.websystem.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum LocationSource {
    PHILIPPINES("Philippines"),
    JAPAN("Japan");

    public static Map<String, String> MAP = new LinkedHashMap<>();

    static {
        for (LocationSource value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private LocationSource(String label) {
        this.label = label;
    }
}