package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private String cityOrCountry;

    @Getter
    @Setter
    private String type;
    // "SHOP", "SOURCE_WAREHOUSE", "CENTRAL_WAREHOUSE" "SOURCE"

    public Location(String name, String cityOrCountry, String type) {
        this.name = name;
        this.type = type;
        this.cityOrCountry = cityOrCountry;
    }
}
