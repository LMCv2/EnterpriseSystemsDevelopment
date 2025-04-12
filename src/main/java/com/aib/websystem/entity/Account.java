package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
public class Account {
    @Id
    @Getter
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
