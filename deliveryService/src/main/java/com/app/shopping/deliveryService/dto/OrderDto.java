package com.app.shopping.deliveryService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	long orderId;
	String orderName;
	String orderStatus;
	Double paymentAmount;
}
