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
public class Event {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    private Fruit fruit;

    @Getter
    @Setter
    private Integer quantity;

    @Getter
    @Setter
    private EventType eventType;

    @Getter
    @Setter
    @ManyToOne
    private Location fromLocation;

    @Getter
    @Setter
    @ManyToOne
    private Location toLocation;

    public Event(Fruit fruit, Integer quantity) {
        this(fruit, quantity, EventType.CONSUMPTION, null, null);
    }

    public Event(Fruit fruit, Integer quantity, EventType eventType, Location fromLocation, Location toLocation) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.eventType = eventType;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }
}
