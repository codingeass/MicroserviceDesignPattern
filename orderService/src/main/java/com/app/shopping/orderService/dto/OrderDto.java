package com.app.shopping.orderService.dto;


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
	String deliveryLocation;
	Double paymentAmount;
}
