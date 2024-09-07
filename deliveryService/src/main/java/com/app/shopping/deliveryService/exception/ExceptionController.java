package com.app.shopping.deliveryService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(DeliveryCreationException.class)
	public void handleOrderException() {
		
	}
}
