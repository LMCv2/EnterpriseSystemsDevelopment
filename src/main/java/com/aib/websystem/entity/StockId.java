package com.aib.websystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StockId {
    @Getter
    private Fruit fruit;
    @Getter
    private Location location;
}
