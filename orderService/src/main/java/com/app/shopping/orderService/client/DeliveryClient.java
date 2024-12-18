package com.app.shopping.orderService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.shopping.orderService.dto.DeliveryDto;

@Component
@FeignClient(name ="DeliveryClient", url="${service.api.delivery}")
public interface DeliveryClient {

	@PostMapping("/bookOrderDelivery")
	public DeliveryDto bookDelivery(@RequestBody DeliveryDto deliveryDto);
	
}
