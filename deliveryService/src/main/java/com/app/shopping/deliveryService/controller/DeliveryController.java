package com.app.shopping.deliveryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.shopping.deliveryService.dto.DeliveryDto;
import com.app.shopping.deliveryService.dto.OrderDto;
import com.app.shopping.deliveryService.exception.DeliveryCreationException;
import com.app.shopping.deliveryService.service.DeliveryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	DeliveryService deliveryService;

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value ="/book")
	public DeliveryDto bookOrderDelivery(@RequestBody DeliveryDto deliveryDto) throws DeliveryCreationException {
		log.info("Delivery Service Triggered for OrderId {}", deliveryDto.getOrderId());
		return deliveryService.bookDeliveryStatus(deliveryDto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping
	public void revertOrderDelivery(Long orderId) {
		log.info("Delivery orderId {} reverse request received", orderId);
		deliveryService.revertOrderDelivery(orderId);
	}
	
}
