package com.app.shopping.deliveryService.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.app.shopping.deliveryService.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.app.shopping.deliveryService.dto.DeliveryDto;
import com.app.shopping.deliveryService.exception.DeliveryCreationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	StreamBridge streamBridge;
	
	@Override
	public DeliveryDto bookDeliveryStatus(DeliveryDto deliveryDto) throws DeliveryCreationException {
		if("Lucknow".equalsIgnoreCase(deliveryDto.getDeliveryLocation())) {
			log.error("Delivery Failed for OrderId {}", deliveryDto.getOrderId());
			streamBridge.send("reverseOrder-out-0", (Long)deliveryDto.getOrderId());
			throw new DeliveryCreationException("Failed to deliver order");
		}
		log.info("Calling Payment Service for OrderId {}", deliveryDto.getOrderId());
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(deliveryDto, orderDto);
		streamBridge.send("confirmPayment-out-0", orderDto);
		return deliveryDto;
	}

	@Override
	public void revertOrderDelivery(Long orderId) {
		log.info("Delivery of the OrderId {} reverted", orderId);
	}
	
}
