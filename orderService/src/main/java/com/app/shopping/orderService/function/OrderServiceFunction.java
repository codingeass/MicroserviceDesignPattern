package com.app.shopping.orderService.function;

import com.app.shopping.orderService.dto.OrderDto;
import com.app.shopping.orderService.exception.OrderCreationException;
import com.app.shopping.orderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderServiceFunction {

    @Autowired
    OrderService orderService;

    @Bean
    public Consumer<OrderDto> updateOrderStatus(){
        return orderDto -> {
            log.info("Update Order Status Request Triggered for Order Id {}", orderDto.getOrderId());
            orderService.updateOrderStatus(orderDto);
        };
    }

    @Bean
    public Consumer<Long> reverseOrder(){
        return orderId -> {
            log.info("Order Reversal Request Triggered for Order Id {}", orderId);
            orderService.reverseOrder(orderId);
        };
    }
}
