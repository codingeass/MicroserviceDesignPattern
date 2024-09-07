package com.app.shopping.paymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.shopping.paymentService.exception.OrderCreationException;
import com.app.shopping.paymentService.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value ="/confirm/{orderId}")
	public void confirmPayment(@PathVariable Long orderId, @RequestParam("paymentAmount") Double paymentAmount) throws OrderCreationException {
		paymentService.confirmPayment(orderId, paymentAmount);
	}
	
}
