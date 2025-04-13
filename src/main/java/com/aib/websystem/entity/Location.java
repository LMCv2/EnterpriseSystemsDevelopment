package com.aib.websystem.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
public class Location {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String cityName;

    @Getter
    @Setter
    private LocationType type;

    @Getter
    @OneToMany
    private List<Stock> stock;

    @Getter
    @Setter
    private LocationSource locationCentre;

    public Location(String name, String cityName, LocationType type) {
        this(name, cityName, type, null);
    }

    public Location(String name, String cityName, LocationType type, LocationSource locationCentre) {
        this.name = name;
        this.cityName = cityName;
        this.type = type;
        this.locationCentre = locationCentre;
    }
}
