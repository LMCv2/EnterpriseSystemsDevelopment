package com.aib.websystem.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Stock implements Serializable {
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

    @Getter
    @CreatedDate
    private Date createDate;

    @Getter
    @LastModifiedDate
    private Date lastModifiedDate;

    public Stock(Fruit fruit, Location location, Integer quantity) {
        this.fruit = fruit;
        this.location = location;
        this.quantity = quantity;
    }
}
