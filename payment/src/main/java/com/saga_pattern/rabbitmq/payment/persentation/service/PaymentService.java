package com.saga_pattern.rabbitmq.payment.persentation.service;

import com.saga_pattern.rabbitmq.payment.domain.entity.Payment;
import com.saga_pattern.rabbitmq.payment.domain.entity.enums.CancelType;
import com.saga_pattern.rabbitmq.payment.domain.entity.enums.PaymentStatus;
import com.saga_pattern.rabbitmq.payment.domain.repository.PaymentRepository;
import com.saga_pattern.rabbitmq.payment.message.DeliveryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository paymentRepository;

    @Value("${message.queue.err.product}")
    private String productErrorQueue;

    public void  createPayment(DeliveryMessage deliveryMessage) {

        Integer payAmount = deliveryMessage.getPayAmount();
        PaymentStatus status = PaymentStatus.SUCCESS;

        //TEMPORARY ISSUE MAKER
        if (deliveryMessage.getPayAmount() >= 10000) {
            log.error("Payment amount exceeds limit: {}", payAmount);
            status = PaymentStatus.CANCEL;
            deliveryMessage.setErrorType(CancelType.PAYMENT_LIMIT_EXCEEDED);
            this.rollbackPayment(deliveryMessage);
        }

        Payment payment = Payment.of(
                UUID.randomUUID(), deliveryMessage.getOrderId(),
                deliveryMessage.getUserId(), String.valueOf(payAmount), status);

        Payment saved = paymentRepository.save(payment);
        System.out.println("saved = " + saved);

    }

    public void rollbackPayment(DeliveryMessage deliveryMessage) {
        log.info("PAYMENT ROLLBACK !!!");
        rabbitTemplate.convertAndSend(productErrorQueue, deliveryMessage);
    }
}
