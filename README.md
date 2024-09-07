# MicroserviceDesignPattern
Implementation of various microservice design patterns

## Simple Saga Pattern Implementation

### Framework/Language/Library Used for Implementation
1. **Spring Boot Starter Web**
2. **Java 21**
3. **OpenFeign**
4. **OpenApi**

![Saga Pattern Flowchart](https://raw.githubusercontent.com/codingeass/MicroserviceDesignPattern/main/Artifact/Flowchart%20-%20Saga%20Pattern%20-%20Each%20Service%20Calling%20Compensating%20Transaction.jpg)

In this application, invoking the order service endpoint (http://localhost:8080/order/update/status - SwaggerUI is available at http://localhost:8080/swagger-ui/index.html) to update the order status with a payment amount less than 2000 and a delivery location other than Lucknow will trigger the services successfully.

### Example

**POST**: `http://localhost:8080/order/update/status`
```json
{
  "orderId": 200,
  "orderName": "Pokemon",
  "deliveryLocation": "Delhi",
  "paymentAmount": 200
}
```

Following logs are printed:
```
[order] [io-8080-exec-10] c.a.s.o.controller.OrderController       : Update Order Status Request Triggered for Order Id 200
[delivery] [nio-8081-exec-2] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
[delivery] [nio-8081-exec-2] c.a.s.d.service.DeliveryServiceImpl      : Calling Payment Service for OrderId 200
[paymentService] [nio-8082-exec-1] c.a.s.p.service.PaymentServiceImpl       : Payment for OrderId received 200
[paymentService] [nio-8082-exec-1] c.a.s.p.service.PaymentServiceImpl       : Payment Successful for OrderId 200
```

**POST**: `http://localhost:8080/order/update/status`
```json
{
  "orderId": 200,
  "orderName": "Pokemon",
  "deliveryLocation": "Lucknow",
  "paymentAmount": 200
}
```

The logs below indicate that when the delivery failed due to the location constraint, the delivery service triggered compensating transactions for the order service, which then reverted the transaction.

```
[order] [nio-8080-exec-4] c.a.s.o.controller.OrderController       : Order Reversal Request Triggered for Order Id 200
[order] [pool-1-thread-1] c.a.s.o.service.OrderServiceImpl         : Error : [406] during [PUT] to [http://localhost:8081/delivery/book] [DeliveryClient#bookDelivery(DeliveryDto)]: [] {}
[delivery] [nio-8081-exec-5] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
[delivery] [nio-8081-exec-5] c.a.s.d.service.DeliveryServiceImpl      : Delivery Failed for OrderId 200
[delivery] [nio-8081-exec-5] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [com.app.shopping.deliveryService.exception.DeliveryCreationException: Failed to deliver order]
[order] [nio-8080-exec-4] c.a.s.o.service.OrderServiceImpl         : Reverting OrderId 200
```



**POST**: `http://localhost:8080/order/update/status`
```json
{
  "orderId": 200,
  "orderName": "Pokemon",
  "deliveryLocation": "Delhi",
  "paymentAmount": 4000
}
```

In this scenario, where the correct delivery location is selected but the payment amount is incorrect, the compensating transactions for both the order and delivery services are triggered by the payment service. The implementation can also be adjusted so that the payment service triggers the compensating transaction for the delivery service only, and then the delivery service triggers the reversal of the order if they need to be executed sequentially.

The following logs are generated:
```
[order] [nio-8080-exec-9] c.a.s.o.controller.OrderController       : Order Reversal Request Triggered for Order Id 200
[delivery] [nio-8081-exec-7] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
[delivery] [nio-8081-exec-7] c.a.s.d.service.DeliveryServiceImpl      : Calling Payment Service for OrderId 200
[paymentService] [nio-8082-exec-4] c.a.s.p.service.PaymentServiceImpl       : Payment for OrderId received 200
[paymentService] [nio-8082-exec-4] c.a.s.p.service.PaymentServiceImpl       : Reverting OrderId 200 due to payment being greater than 2000
[delivery] [nio-8081-exec-4] c.a.s.d.controller.DeliveryController    : Delivery orderId 200 reverse request received
[delivery] [nio-8081-exec-4] c.a.s.d.service.DeliveryServiceImpl      : Delivery of the OrderId null reverted
[order] [nio-8080-exec-9] c.a.s.o.service.OrderServiceImpl         : Reverting OrderId 200
```

