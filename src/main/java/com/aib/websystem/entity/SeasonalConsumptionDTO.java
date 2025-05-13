package com.aib.websystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class SeasonalConsumptionDTO implements Serializable {
    @Getter
    private String name;

    @Getter
    private Integer year;

    @Getter
    private String season;

    @Getter
    private String fruitName;

    @Getter
    private Long quantity;

    public SeasonalConsumptionDTO(String name, Integer year, String season, String fruitName, Long quantity) {
        this.name = name;
        this.year = year;
        this.season = season;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }
}
