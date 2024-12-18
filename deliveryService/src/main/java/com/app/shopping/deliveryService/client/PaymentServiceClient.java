package com.app.shopping.deliveryService.client;

import com.app.shopping.deliveryService.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name ="PaymentServiceClient", url="${service.api.payment}")
public interface PaymentServiceClient {

	@PostMapping("/confirmPayment")
	public void confirmPayment(@RequestBody OrderDto orderDto);

}
