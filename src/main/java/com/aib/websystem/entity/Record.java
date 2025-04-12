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
public class Record {
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
    private RecordType recordType;

    @Getter
    @Setter
    @ManyToOne
    private Location fromLocation;

    @Getter
    @Setter
    @ManyToOne
    private Location toLocation;

    public Record(Fruit fruit, Integer quantity) {
        this(fruit, quantity, RecordType.CONSUPTION, null, null);
    }

    public Record(Fruit fruit, Integer quantity, RecordType recordType, Location fromLocation, Location toLocation) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.recordType = recordType;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }
}
