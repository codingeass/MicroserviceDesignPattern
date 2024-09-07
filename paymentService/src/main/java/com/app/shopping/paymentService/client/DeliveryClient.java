package com.app.shopping.paymentService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;


@Component
@FeignClient(name ="DeliveryClient", url="${service.api.delivery}")
public interface DeliveryClient {

	@DeleteMapping
	public void revertOrderDelivery(Long orderId);
	
}
