package com.app.shopping.orderService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;
import com.app.shopping.orderService.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value ="/update/status")
	public void updateOrderStatus(@RequestBody OrderDto orderDto) throws OrderCreationException {
		log.info("Update Order Status Request Triggered for Order Id {}", orderDto.getOrderId());
		orderService.updateOrderStatus(orderDto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value ="/reverse/{orderId}")
	public void reverseOrder(@PathVariable Long orderId) throws OrderCreationException {
		log.info("Order Reversal Request Triggered for Order Id {}", orderId);
		orderService.reverseOrder(orderId);
	}
	
}
