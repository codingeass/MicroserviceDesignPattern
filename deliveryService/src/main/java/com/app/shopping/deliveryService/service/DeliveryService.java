package com.app.shopping.deliveryService.service;

import com.app.shopping.deliveryService.dto.DeliveryDto;
import com.app.shopping.deliveryService.exception.DeliveryCreationException;

public interface DeliveryService {

	DeliveryDto bookDeliveryStatus(DeliveryDto deliveryDto) throws DeliveryCreationException;

	void revertOrderDelivery(Long orderId);

}
