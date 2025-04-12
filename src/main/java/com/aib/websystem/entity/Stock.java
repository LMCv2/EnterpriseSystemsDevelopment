package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
public class Stock {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @ManyToOne
    private Fruit fruit;

    @Getter
    @ManyToOne
    private Location location;

    @Getter
    @Setter
    private Integer quantity;

    public Stock(Fruit fruit, Location location, Integer quantity) {
        this.fruit = fruit;
        this.location = location;
        this.quantity = quantity;
    }
}
