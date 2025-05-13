package com.aib.websystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class DeliveryForecastDTO implements Serializable {
    @Getter
    private Fruit fruit;

    @Getter
    private Location sourceCountry;

    @Getter
    private Location targetCountry;

    @Getter
    private double averageDeliveryDays;

    @Getter
    private double standardDeviationDays;

    public DeliveryForecastDTO(Fruit fruit, Location sourceCountry, Location targetCountry, double averageDeliveryDays, double standardDeviationDays) {
        this.fruit = fruit;
        this.sourceCountry = sourceCountry;
        this.targetCountry = targetCountry;
        this.averageDeliveryDays = averageDeliveryDays;
        this.standardDeviationDays = standardDeviationDays;
    }
}
