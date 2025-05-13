package com.aib.websystem.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class ReserveNeedDTO implements Serializable {
    @Getter
    private String name;

    @Getter
    private String fruitName;

    @Getter
    private Long quantity;

    public ReserveNeedDTO(String name, String fruitName, Long quantity) {
        this.name = name;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }
}
