package com.app.shopping.orderService.service;

import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;

public interface OrderService {

	OrderDto updateOrderStatus(OrderDto orderDto) throws OrderCreationException;

	void reverseOrder(Long orderId);

}
