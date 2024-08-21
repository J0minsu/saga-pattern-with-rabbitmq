package com.saga_pattern.rabbitmq.product.presentation.service;

import com.saga_pattern.rabbitmq.product.domain.entity.Product;
import com.saga_pattern.rabbitmq.product.domain.entity.enums.CancelType;
import com.saga_pattern.rabbitmq.product.domain.repository.ProductRepository;
import com.saga_pattern.rabbitmq.product.message.DeliveryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;

    @Value("${message.queue.payment}")
    private String paymentQueue;

    @Value("${message.queue.err.order}")
    private String orderErrorQueue;

    public void reduceProductAmount(DeliveryMessage deliveryMessage) {

        Integer productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();
        Product product = null;
        //Product 조회 후 카운트 차감
        try {
        product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("일치하는 상품이 없습니다."));

        }catch (IllegalStateException e) {
            deliveryMessage.setErrorType(CancelType.NOT_ENOUGH_STOCK);
            rabbitTemplate.convertAndSend(orderErrorQueue, deliveryMessage);
            return;
        }

        //if 주문보다 차감치가 많을 경우 에러 발생
        try {
            product.reduceStock(productQuantity);
        }
        catch (IllegalArgumentException e) {
            this.rollbackProduct(deliveryMessage);
            return;
        }

        rabbitTemplate.convertAndSend(paymentQueue,deliveryMessage);

    }

    //Temporary Exception
    public void rollbackProduct(DeliveryMessage deliveryMessage){

        log.info("TEMPORARY ROLLBACK!!!");
        rabbitTemplate.convertAndSend(orderErrorQueue, deliveryMessage);

    }

    public Product entryProduct() {

        Product product = Product.of("감바스", "스페인산 육쪽마늘 베이스", 36000, 6, true);
        Product entriedProduct = productRepository.save(product);

        return entriedProduct;
    }
}

