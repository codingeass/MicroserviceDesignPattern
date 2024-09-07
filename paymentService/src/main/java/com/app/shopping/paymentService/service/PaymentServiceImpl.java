package com.app.shopping.paymentService.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.shopping.paymentService.client.DeliveryClient;
import com.app.shopping.paymentService.client.OrderClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{
	
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@Autowired
	DeliveryClient deliveryClient;
	
	@Autowired
	OrderClient orderClient;

	@Override
	public void confirmPayment(Long orderId, Double paymentAmount) {
		log.info("Payment for OrderId received {}", orderId);
		if(paymentAmount > 2000) {
			log.error("Reverting OrderId {} due to payment being greater than 2000", orderId);
			executorService.submit(() -> {
				orderClient.revertOrder(orderId);
				deliveryClient.revertOrderDelivery(orderId);
			});
			return;
		}
		log.info("Payment Successful for OrderId {}", orderId);
	}
	
}
