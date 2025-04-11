package com.aib.websystem.entity;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    SHOP_STAFF("Shop Staff"),
    SOURCE_WAREHOUSE_STAFF("Source Warehouse Staff"),
    CENTRAL_WAREHOUSE_STAFF("Central Warehouse Staff"),
    SENIOR_MANAGEMENT("Senior Management");

    public static Map<String, String> MAP = new HashMap<>();

    static {
        for (Role value : values()) {
            MAP.put(value.name(), value.label);
        }
    }

    private String label;

    private Role(String label) {
        this.label = label;
    }
}
