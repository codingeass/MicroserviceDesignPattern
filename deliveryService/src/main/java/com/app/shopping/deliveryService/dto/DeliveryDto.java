package com.app.shopping.deliveryService.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeliveryDto {
	long orderId;
	String deliveryLocation;
	String status;
	Double paymentAmount;
}
