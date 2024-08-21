package com.saga_pattern.rabbitmq.order.domain.order.entity;

import com.saga_pattern.rabbitmq.order.domain.order.entity.enums.CancelType;
import com.saga_pattern.rabbitmq.order.domain.order.entity.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Optional;
import java.util.UUID;

@ToString
@Entity(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate
public class Order {

    @Id
    private UUID orderId;
    private String userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private CancelType cancelType;

    public void cancelOrder(CancelType errorType) {
        orderStatus = OrderStatus.CANCEL;
        cancelType = errorType;
    }

    public static Order of(UUID orderId, String userId, OrderStatus orderStatus, CancelType cancelType) {
        return new Order(orderId, userId, orderStatus, cancelType);
    }

}

