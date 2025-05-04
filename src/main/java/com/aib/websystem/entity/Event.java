package com.aib.websystem.entity;

import java.util.Date;

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

    @Getter
    @Setter
    @ManyToOne
    private Location OriginalFromLocation;

    @Getter
    @Setter
    @ManyToOne
    private Location FinalToLocation;

    @Getter
    @Setter
    private Date eventDate;

    @Getter
    @Setter
    private EventStatus status;

    public Event(Fruit fruit, Integer quantity) {
        this(fruit, quantity, EventType.CONSUMPTION, null, null, null, null, null, null);
    }
    
    public Event(Fruit fruit, Integer quantity, EventType eventType, Location fromLocation, Location toLocation, Date eventDate, EventStatus status) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.eventType = eventType;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.eventDate = eventDate;
        this.status = status;
    }

    public Event(Fruit fruit, Integer quantity, EventType eventType, Location fromLocation, Location toLocation, Location originalFromLocation, Location finalToLocation, Date eventDate, EventStatus status) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.eventType = eventType;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.OriginalFromLocation = originalFromLocation;
        this.FinalToLocation = finalToLocation;
        this.eventDate = eventDate;
        this.status = status;
    }
}
