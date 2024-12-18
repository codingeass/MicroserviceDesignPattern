package com.app.shopping.paymentService.exception;

public class OrderCreationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public OrderCreationException(String s){
		super(s);
	}

}
