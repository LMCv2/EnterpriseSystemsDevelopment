package com.aib.websystem.entity;

import java.io.Serializable;
import java.util.Calendar;
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

    @Getter
    @Setter
    private Integer year;

    @Getter
    @Setter
    private String season;

    public Event(Fruit fruit, Long quantity, Location location) {
        this(fruit, quantity, EventType.CONSUMPTION, location, null, null, EventStatus.CONFIRMED);
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

        Date now = new Date();
        this.timePeriod = TimePeriodConverter.convertToTimePeriod(now);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        this.year = calendar.get(Calendar.YEAR);
        this.season = switch (calendar.get(Calendar.MONTH) + 1) {
            case 3, 4, 5 -> "Spring";
            case 6, 7, 8 -> "Summer";
            case 9, 10, 11 -> "Autumn";
            case 12, 1, 2 -> "Winter";
            default -> "Unknown";
        };
    }
}
