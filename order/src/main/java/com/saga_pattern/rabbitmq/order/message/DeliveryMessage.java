package com.saga_pattern.rabbitmq.order.message;

import com.saga_pattern.rabbitmq.order.domain.order.entity.enums.CancelType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMessage {
    private UUID orderId;
    private UUID paymentId;

    private String userId;

    private Integer productId;
    private Integer productQuantity;

    private Integer payAmount;

    private CancelType errorType;
}

