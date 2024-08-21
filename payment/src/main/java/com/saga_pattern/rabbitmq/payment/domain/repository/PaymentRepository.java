package com.saga_pattern.rabbitmq.payment.domain.repository;

import com.saga_pattern.rabbitmq.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
