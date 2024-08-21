package com.saga_pattern.rabbitmq.product.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String name;

    private String description;

    private Integer price;

    private Integer stock;

    private boolean isUsable;


    protected Product(String name, String description, Integer price, Integer stock, boolean isUsable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isUsable = isUsable;
    }

    public static Product of(String name, String description, Integer price, Integer stock, boolean isUsable) {
        return new Product(name, description, price, stock, isUsable);
    }

    public void reduceStock(Integer stock) {

        this.stock -= stock;

    }

    public void increaseStock(Integer stock) {
        this.stock += stock;
    }

}
