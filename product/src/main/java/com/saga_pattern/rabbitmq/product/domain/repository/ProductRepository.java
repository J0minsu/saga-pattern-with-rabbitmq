package com.saga_pattern.rabbitmq.product.domain.repository;

import com.saga_pattern.rabbitmq.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
