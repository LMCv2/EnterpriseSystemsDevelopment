package com.aib.websystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class AddLocation implements Serializable {
    @Getter
    @Setter
    private Location location;

    public AddLocation(Location location) {
        this.location = location;
    }
}
