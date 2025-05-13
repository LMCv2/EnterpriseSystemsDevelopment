package com.aib.websystem.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.aib.websystem.util.TimePeriodConverter;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class Event implements Serializable {
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
    private Long quantity;

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
    private Location throughLocation;

    @Getter
    @Setter
    @ManyToOne
    private Location toLocation;

    @Getter
    @Setter
    private EventStatus status;

    @Getter
    private Integer timePeriod;

    @Getter
    @CreatedDate
    private Date createDate;

    @Getter
    @LastModifiedDate
    private Date lastModifiedDate;

    public Event(Fruit fruit, Long quantity) {
        this(fruit, quantity, EventType.CONSUMPTION, null, null, null, EventStatus.CONFIRMED);
    }

    public Event(Fruit fruit, Long quantity, Location fromLocation, Location toLocation) {
        this(fruit, quantity, EventType.BORROWING, fromLocation, null, toLocation, EventStatus.PENDING);
    }

    public Event(Fruit fruit, Long quantity, Location fromLocation, Location throughLocation, Location toLocation) {
        this(fruit, quantity, EventType.RESERVATION, fromLocation, throughLocation, toLocation, EventStatus.PENDING);
    }

    public Event(Fruit fruit, Long quantity, EventType eventType, Location fromLocation, Location throughLocation, Location toLocation, EventStatus eventStatus) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.eventType = eventType;
        this.fromLocation = fromLocation;
        this.throughLocation = throughLocation;
        this.toLocation = toLocation;
        this.status = eventStatus;
        this.timePeriod = TimePeriodConverter.convertToTimePeriod(new Date());
    }
}
