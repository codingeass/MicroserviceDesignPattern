package com.app.shopping.orderService.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.app.shopping.orderService.dto.DeliveryDto;
import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

<<<<<<< Updated upstream
	@Autowired
	DeliveryClient deliveryClient;
	
	@Override
	public OrderDto updateOrderStatus(OrderDto orderDto) throws OrderCreationException {
		if(orderDto.getOrderId() == 400) {
			log.error("Error booking order with OrderId {}", orderDto.getOrderId());
			throw new OrderCreationException("Not able to create order" + orderDto.getOrderName());
		}
		CompletableFuture.runAsync(() -> {
				DeliveryDto deliveryDto = DeliveryDto.builder()
						.deliveryLocation(orderDto.getDeliveryLocation())
						.orderId(orderDto.getOrderId())
						.status("PENDING")
						.paymentAmount(orderDto.getPaymentAmount())
						.build();
				deliveryClient.bookDelivery(deliveryDto);
		});
		return orderDto;
	}

	@Override
	public void reverseOrder(Long orderId) {
		log.info("Reverting OrderId {}", orderId);
	}
	
=======
    @Autowired
    private OrderService self; // Self-invocation workaround

    @Autowired
    StreamBridge streamBridge;

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "orderServiceBookRetry")
    public OrderDto updateOrderStatus(OrderDto orderDto) throws OrderCreationException {
        self.issueCheck(orderDto); // Use self-invocation
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryLocation(orderDto.getDeliveryLocation())
                .orderId(orderDto.getOrderId())
                .status("PENDING")
                .paymentAmount(orderDto.getPaymentAmount())
                .build();
        streamBridge.send("bookOrderDelivery-out-0", deliveryDto);
        return orderDto;
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "orderServiceBookRetrySelf")
    public void issueCheck(OrderDto orderDto) throws OrderCreationException {
        if (orderDto.getOrderId() == 400) {
            log.error("Error booking order with OrderId {}", orderDto.getOrderId());
            throw new OrderCreationException("Not able to create order " + orderDto.getOrderName());
        }
    }

    @Override
    public void reverseOrder(Long orderId) {
        log.info("Reverting OrderId {}", orderId);
    }

    @Override
    public OrderDto orderServiceBookRetry(OrderDto orderDto, Exception ex) throws OrderCreationException {
        log.info("Triggering Retry");
        return orderDto;
    }

    @Override
    public void orderServiceBookRetrySelf(OrderDto orderDto, Exception ex) throws OrderCreationException {
        log.info("Triggering Retry");
    }
>>>>>>> Stashed changes
}
