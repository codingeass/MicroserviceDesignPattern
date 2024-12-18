package com.app.shopping.paymentService.function;

import com.app.shopping.paymentService.dto.OrderDto;
import com.app.shopping.paymentService.exception.OrderCreationException;
import com.app.shopping.paymentService.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class PaymentServiceFunction {

    @Autowired
    PaymentService paymentService;

    @Bean
    public Consumer<OrderDto> confirmPayment() throws OrderCreationException {
        return orderDto -> {
            paymentService.confirmPayment(orderDto.getOrderId(), orderDto.getPaymentAmount());
        };
    }
}
