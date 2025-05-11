package com.aib.websystem.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;

@Entity
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Location implements Serializable {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String cityName;

    @Getter
    @Setter
    private LocationType type;

    @Getter
    @OneToMany
    private List<Stock> stock;

    @CreatedDate
    @Getter
    private Date createDate;

    @LastModifiedDate
    @Getter
    private Date lastModifiedDate;

    public Location(String name, String cityName, LocationType type) {
        this.name = name;
        this.cityName = cityName;
        this.type = type;
    }
}
