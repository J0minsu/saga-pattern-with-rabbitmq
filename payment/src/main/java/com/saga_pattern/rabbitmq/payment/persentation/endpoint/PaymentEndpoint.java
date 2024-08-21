package com.saga_pattern.rabbitmq.payment.persentation.endpoint;

import com.saga_pattern.rabbitmq.payment.message.DeliveryMessage;
import com.saga_pattern.rabbitmq.payment.persentation.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEndpoint {

    private final PaymentService paymentService;

    @RabbitListener(queues = "${message.queue.payment}")
    public void receiveMessage(DeliveryMessage deliveryMessage) {
        log.info("PAYMENT RECEIVE : {}", deliveryMessage.toString());
        paymentService.createPayment(deliveryMessage);

    }
}

