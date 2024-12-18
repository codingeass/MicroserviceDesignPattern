package com.app.shopping.paymentService.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	StreamBridge streamBridge;

	@Override
	public void confirmPayment(Long orderId, Double paymentAmount) {
		log.info("Payment for OrderId received {}", orderId);
		if(paymentAmount > 2000) {
			log.error("Reverting OrderId {} due to payment being greater than 2000", orderId);
			streamBridge.send("reverseOrder-out-0", orderId);
			streamBridge.send("revertOrderDelivery-out-0", orderId);
			return;
		}
		log.info("Payment Successful for OrderId {}", orderId);
	}
	
}
