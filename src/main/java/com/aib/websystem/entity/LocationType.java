package com.aib.websystem.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum LocationType {
    SOURCE_WAREHOUSE("Source Warehouse"),
    CENTRAL_WAREHOUSE("Central Warehouse"),
    SHOP("Shop");

    public static Map<String, String> MAP = new HashMap<>();

    static {
        for (LocationType value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private LocationType(String label) {
        this.label = label;
    }
}
