package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Role role;

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
