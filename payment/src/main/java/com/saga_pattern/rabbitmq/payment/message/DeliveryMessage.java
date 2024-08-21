package com.saga_pattern.rabbitmq.payment.message;

import com.saga_pattern.rabbitmq.payment.domain.entity.enums.CancelType;
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

