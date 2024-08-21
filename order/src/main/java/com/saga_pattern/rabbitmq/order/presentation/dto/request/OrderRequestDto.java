package com.saga_pattern.rabbitmq.order.presentation.dto.request;

import com.saga_pattern.rabbitmq.order.domain.order.entity.Order;
import com.saga_pattern.rabbitmq.order.domain.order.entity.enums.OrderStatus;
import com.saga_pattern.rabbitmq.order.message.DeliveryMessage;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestDto {

    private String userId;
    private Integer productId;
    private Integer productQuantity;
    private Integer payAmount;

    public Order toOrder (){
        return Order.of(UUID.randomUUID(), userId, OrderStatus.RECEIPT, null);

    }

    public DeliveryMessage toDeliveryMessage(UUID orderId){
        return DeliveryMessage.builder()
                .orderId(orderId)
                .productId(productId)
                .productQuantity(productQuantity)
                .payAmount(payAmount)
            .build();
    }
}
