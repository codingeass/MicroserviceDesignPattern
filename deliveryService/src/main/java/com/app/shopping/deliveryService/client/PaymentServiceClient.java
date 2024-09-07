package com.app.shopping.deliveryService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name ="PaymentServiceClient", url="${service.api.payment}")
public interface PaymentServiceClient {

	@GetMapping("/confirm/{orderId}")
	public void confirmPayment(@PathVariable Long orderId, @RequestParam("paymentAmount") Double paymentAmount);

}
