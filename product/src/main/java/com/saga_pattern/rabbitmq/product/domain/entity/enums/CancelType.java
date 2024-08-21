package com.saga_pattern.rabbitmq.product.domain.entity.enums;

import lombok.Getter;

@Getter
public enum CancelType {

    NOT_ENOUGH_STOCK,
    CHOOSE_OTHER_ITEM,
    ETC,
    ;

}

