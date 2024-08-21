package com.saga_pattern.rabbitmq.payment.domain.entity;

import com.saga_pattern.rabbitmq.payment.domain.entity.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
public class Payment {

    @Id
    private UUID paymentId;
    private UUID orderId;
    private String userId;
    private String payAmount;
    private PaymentStatus payStatus;

    public static Payment of(UUID paymentId, UUID orderId, String userId, String payAmount, PaymentStatus payStatus) {
        return new Payment(paymentId, orderId, userId, payAmount, payStatus);
    }

    public void cancel() {
        this.payStatus = PaymentStatus.CANCEL;
    }

}
