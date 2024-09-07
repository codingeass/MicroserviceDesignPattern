package com.app.shopping.deliveryService.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.shopping.deliveryService.client.OrderClient;
import com.app.shopping.deliveryService.client.PaymentServiceClient;
import com.app.shopping.deliveryService.dto.DeliveryDto;
import com.app.shopping.deliveryService.exception.DeliveryCreationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@Autowired
	OrderClient orderClient;
	
	@Autowired
	PaymentServiceClient paymentServiceClient;
	
	@Override
	public DeliveryDto bookDeliveryStatus(DeliveryDto deliveryDto) throws DeliveryCreationException {
		if("Lucknow".equalsIgnoreCase(deliveryDto.getDeliveryLocation())) {
			log.error("Delivery Failed for OrderId {}", deliveryDto.getOrderId());
			executorService.execute(() -> {
				orderClient.revertOrder((Long)deliveryDto.getOrderId());
			});
			throw new DeliveryCreationException("Failed to deliver order");
		}
		log.info("Calling Payment Service for OrderId {}", deliveryDto.getOrderId());
		executorService.execute(() -> {
			paymentServiceClient.confirmPayment(deliveryDto.getOrderId(), deliveryDto.getPaymentAmount());
		});
		return deliveryDto;
	}

	@Override
	public void revertOrderDelivery(Long orderId) {
		log.info("Delivery of the OrderId {} reverted", orderId);
	}
	
}
