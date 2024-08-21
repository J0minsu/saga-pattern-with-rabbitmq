package com.saga_pattern.rabbitmq.product.presentation.endpoint;

import com.saga_pattern.rabbitmq.product.domain.entity.Product;
import com.saga_pattern.rabbitmq.product.message.DeliveryMessage;
import com.saga_pattern.rabbitmq.product.presentation.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
@RequiredArgsConstructor
@RestController
public class ProductEndpoint {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct() {
        Product savedProduct = productService.entryProduct();
        return ResponseEntity.ok(savedProduct);
    }

    @RabbitListener(queues = "${message.queue.product}")
    public void receiveMessage(DeliveryMessage deliveryMessage) {
        productService.reduceProductAmount(deliveryMessage);

        log.info("PRODUCT RECEIVE:{}", deliveryMessage.toString());
    }

    @RabbitListener(queues="${message.queue.err.product}")
    public void receiveErrorMessage(DeliveryMessage deliveryMessage) {
        log.info("ERROR RECEIVE !!!");
        productService.rollbackProduct(deliveryMessage);
    }
}
