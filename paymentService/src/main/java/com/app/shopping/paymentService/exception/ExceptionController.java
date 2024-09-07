package com.app.shopping.paymentService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(OrderCreationException.class)
	public void handleOrderException() {
		
	}
}
