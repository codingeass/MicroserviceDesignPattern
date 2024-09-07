package com.app.shopping.paymentService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name ="OrderClient", url="${service.api.order}")
public interface OrderClient {

	@GetMapping("/reverse/{orderId}")
	public void revertOrder(@PathVariable Long orderId);

}
