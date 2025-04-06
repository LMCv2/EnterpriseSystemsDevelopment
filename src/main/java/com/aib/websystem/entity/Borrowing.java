package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Borrowing {
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
    @ManyToOne
    private Location lenderShop;

    @Getter
    @Setter
    @ManyToOne
    private Location borrowerShop;
}
