package com.app.shopping.paymentService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient(name ="DeliveryClient", url="${service.api.delivery}")
public interface DeliveryClient {

	@PostMapping("/revertOrderDelivery")
	public void revertOrderDelivery(@RequestBody Long orderId);
	
}
