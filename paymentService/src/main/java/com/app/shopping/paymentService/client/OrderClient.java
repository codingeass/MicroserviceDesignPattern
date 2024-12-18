package com.app.shopping.paymentService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name ="OrderClient", url="${service.api.order}")
public interface OrderClient {

	@PostMapping("/reverseOrder")
	public void revertOrder(@RequestBody Long orderId);

}
