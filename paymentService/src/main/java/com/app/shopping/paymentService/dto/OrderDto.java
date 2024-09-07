package com.app.shopping.paymentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	long orderId;
	String orderName;
	String orderStatus;
	
}
