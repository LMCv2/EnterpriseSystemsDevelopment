package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@IdClass(StockId.class)
@ToString
@NoArgsConstructor
public class Stock {
    @Id
    @Getter
    @ManyToOne
    private Fruit fruit;

    @Id
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
