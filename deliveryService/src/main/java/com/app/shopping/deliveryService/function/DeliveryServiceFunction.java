package com.app.shopping.deliveryService.function;

import com.app.shopping.deliveryService.dto.DeliveryDto;
import com.app.shopping.deliveryService.exception.DeliveryCreationException;
import com.app.shopping.deliveryService.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class DeliveryServiceFunction {

    @Autowired
    DeliveryService deliveryService;

    @Bean
    public Function<DeliveryDto, DeliveryDto> bookOrderDelivery() throws DeliveryCreationException {
        return deliveryDto -> {
            log.info("Delivery Service Triggered1 for OrderId {}", deliveryDto.getOrderId());
            return deliveryService.bookDeliveryStatus(deliveryDto);
        };
    }

    @Bean
    public Consumer<Long> revertOrderDelivery() {
        return orderId -> {
            log.info("Delivery orderId {} reverse request received", orderId);
            deliveryService.revertOrderDelivery(orderId);
        };
    }
}
