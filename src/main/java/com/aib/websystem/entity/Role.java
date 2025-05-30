package com.aib.websystem.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum Role {
    ADMIN("Admin"),
    SHOP_STAFF("Shop Staff"),
    SOURCE_WAREHOUSE_STAFF("Source Warehouse Staff"),
    CENTRAL_WAREHOUSE_STAFF("Central Warehouse Staff"),
    SENIOR_MANAGEMENT("Senior Management");

    public static Map<String, String> MAP = new LinkedHashMap<>();

    static {
        for (Role value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    @Getter
    private String label;

    private Role(String label) {
        this.label = label;
    }
}
