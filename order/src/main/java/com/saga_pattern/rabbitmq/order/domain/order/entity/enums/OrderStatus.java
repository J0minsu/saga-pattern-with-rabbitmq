package com.saga_pattern.rabbitmq.order.domain.order.entity.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    CANCEL,
    DONE,
    RECEIPT,
    CREATED,
    DELIVERED,
    ;

}
