package com.aib.websystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class AddLocation implements Serializable {
    @Getter
    private Location location;

    public AddLocation(Location location) {
        this.location = location;
    }
}
