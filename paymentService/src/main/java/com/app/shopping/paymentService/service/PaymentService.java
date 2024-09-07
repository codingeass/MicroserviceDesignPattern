package com.app.shopping.paymentService.service;

public interface PaymentService {

	void confirmPayment(Long orderId, Double paymentAmount);

}
