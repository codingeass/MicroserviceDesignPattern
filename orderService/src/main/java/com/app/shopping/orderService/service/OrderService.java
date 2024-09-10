package com.app.shopping.orderService.service;

import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;

public interface OrderService {

	OrderDto updateOrderStatus(OrderDto orderDto) throws OrderCreationException;

	void reverseOrder(Long orderId);

	void issueCheck(OrderDto orderDto) throws OrderCreationException;

	public OrderDto orderServiceBookRetry(OrderDto orderDto, Exception ex) throws OrderCreationException;

	void orderServiceBookRetrySelf(OrderDto orderDto, Exception ex) throws OrderCreationException;
}
