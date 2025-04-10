package com.aib.websystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Account {
    // @Getter
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Long id;
    @Id
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String role;
}
