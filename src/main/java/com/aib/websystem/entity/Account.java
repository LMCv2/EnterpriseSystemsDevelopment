package com.aib.websystem.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account implements Serializable {
    @Id
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Role role;

    @ManyToOne
    @Getter
    @Setter
    private Location location;

    @CreatedDate
    @Getter
    private Date createDate;

    @LastModifiedDate
    @Getter
    private Date lastModifiedDate;

    public Account(String username, String password, Role role) {
        this(username, password, role, null);
    }

    public Account(String username, String password, Role role, Location location) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.location = location;
    }
}
