package com.saga_pattern.rabbitmq.order.presentation.service;

import com.saga_pattern.rabbitmq.order.domain.order.repository.OrderRepository;
import com.saga_pattern.rabbitmq.order.presentation.dto.request.OrderRequestDto;
import com.saga_pattern.rabbitmq.order.domain.order.entity.Order;
import com.saga_pattern.rabbitmq.order.message.DeliveryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${message.queue.product}")
    private String productQueue;

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestDto.toOrder();
        DeliveryMessage deliveryMessage = orderRequestDto.toDeliveryMessage(order.getOrderId());

        //order save
        orderRepository.save(order);

        log.info("send Message : {}",deliveryMessage.toString());

        rabbitTemplate.convertAndSend(productQueue, deliveryMessage);

        return order;

    }

    @Transactional
    public void rollbackOrder(DeliveryMessage message) {
        Order order = getOrder(message.getOrderId());

        order.cancelOrder(message.getErrorType());
        log.info(order.toString());

    }

    @Transactional(readOnly = true)
    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                            .orElseThrow(() -> new IllegalStateException("일치하는 주문이 없습니다."));
    }
}

