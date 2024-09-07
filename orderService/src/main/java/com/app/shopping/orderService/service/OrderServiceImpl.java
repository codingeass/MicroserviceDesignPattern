package com.app.shopping.orderService.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.shopping.orderService.client.DeliveryClient;
import com.app.shopping.orderService.dto.DeliveryDto;
import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@Autowired
	DeliveryClient deliveryClient;
	
	@Override
	public OrderDto updateOrderStatus(OrderDto orderDto) throws OrderCreationException {
		if(orderDto.getOrderId() == 400) {
			log.error("Error booking order with OrderId {}", orderDto.getOrderId());
			throw new OrderCreationException("Not able to create order" + orderDto.getOrderName());
		}
		executorService.submit(() -> {
			try {
				DeliveryDto deliveryDto = DeliveryDto.builder()
						.deliveryLocation(orderDto.getDeliveryLocation())
						.orderId(orderDto.getOrderId())
						.status("PENDING")
						.paymentAmount(orderDto.getPaymentAmount())
						.build();
				deliveryClient.bookDelivery(deliveryDto);
			} catch(Exception e) {
				log.error("Error : {} {}", e.getMessage(), e);
			}
		});
		return orderDto;
	}

	@Override
	public void reverseOrder(Long orderId) {
		log.info("Reverting OrderId {}", orderId);
	}
	
}
