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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservationSchedule implements Serializable {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //default today
    @Getter
    private Date deliveredDate;

    //after 3 days, start count 14 days
    @Getter
    private Date startDate;

    //after 14 days of start date
    @Getter
    private Date endDate;

    //after 14 days of delivered date
    @Getter
    private Date nextReservedDate;

    @Getter
    @CreatedDate
    private Date createDate;

    @Getter
    @LastModifiedDate
    private Date lastModifiedDate;

    public ReservationSchedule(Date deliveredDate, Date startDate, Date endDate, Date nextReservedDate) {
        this.startDate = startDate;
        this.deliveredDate = deliveredDate;
        this.endDate = endDate;
        this.nextReservedDate = nextReservedDate;
    }
}