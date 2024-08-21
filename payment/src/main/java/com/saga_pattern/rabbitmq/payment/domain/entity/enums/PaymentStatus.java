package com.saga_pattern.rabbitmq.payment.domain.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    SUCCESS,
    CANCEL,
    WAITING,
    ;

}
