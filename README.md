# MicroserviceDesignPattern
Implementation of various microservice design patterns

### Simple Saga Pattern Implementation ###

#### Framework/Language/Liberary Used for implementation ####
1. Spring Boot Starter Web
2. Java 21
3. OpenFeign
4. OpenApi

![TDD](https://raw.githubusercontent.com/codingeass/MicroserviceDesignPattern/main/Artifact/Flowchart%20-%20Saga%20Pattern%20-%20Each%20Service%20Calling%20Compensating%20Transaction.jpg)

In the above application if we call the order service end point (http://localhost:8080/order/update/status - SwaggerUI is enabled at end point http://localhost:8080/swagger-ui/index.html) to update order status with payment amount < 2000 and delivery location != Lucknow, then services are triggered successfully  

Example: 

POST: http://localhost:8080/order/update/status
      
      {
        "orderId": 200,
        "orderName": "Pokemon",
        "deliveryLocation": "Delhi",
        "paymentAmount": 200
      }

Following logs are printed:


      [order] [io-8080-exec-10] c.a.s.o.controller.OrderController       : Update Order Status Request Triggered for Order Id 200
      [delivery] [nio-8081-exec-2] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
      [delivery] [nio-8081-exec-2] c.a.s.d.service.DeliveryServiceImpl      : Calling Payment Service for OrderId 200
      [paymentService] [nio-8082-exec-1] c.a.s.p.service.PaymentServiceImpl       : Payment for OrderId received 200
      [paymentService] [nio-8082-exec-1] c.a.s.p.service.PaymentServiceImpl       : Payment Successful for OrderId 200


POST: http://localhost:8080/order/update/status
      
      {
        "orderId": 200,
        "orderName": "Pokemon",
        "deliveryLocation": "Lucknow",
        "paymentAmount": 200
      }

From the logs printed below we can see that when delivery was failed because of the location constraint then delivery service trggered the compensating transactions for order service and order service has reverted the transaction.

      [order] [nio-8080-exec-4] c.a.s.o.controller.OrderController       : Order Reversal Request Triggered for Order Id 200
      [order] [pool-1-thread-1] c.a.s.o.service.OrderServiceImpl         : Error : [406] during [PUT] to [http://localhost:8081/delivery/book] [DeliveryClient#bookDelivery(DeliveryDto)]: [] {}
      [delivery] [nio-8081-exec-5] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
      [delivery] [nio-8081-exec-5] c.a.s.d.service.DeliveryServiceImpl      : Delivery Failed for OrderId 200
      [delivery] [nio-8081-exec-5] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [com.app.shopping.deliveryService.exception.DeliveryCreationException: Failed to deliver order]
      [order] [nio-8080-exec-4] c.a.s.o.service.OrderServiceImpl         : Reverting OrderId 200


POST: http://localhost:8080/order/update/status
      
      {
        "orderId": 200,
        "orderName": "Pokemon",
        "deliveryLocation": "Delhi",
        "paymentAmount": 4000
      }

For the scenario were we have selected correct delivery location and but incorrect payment amount. Then compensating transaction of both order and delivery service is triggered from payment service. We can also change implementation so that payment triggers compensating transaction for delivery only and then delivery triggers reversal of order if they were required to be triggered sequentially.

      [order] [nio-8080-exec-9] c.a.s.o.controller.OrderController       : Order Reversal Request Triggered for Order Id 200
      [delivery] [nio-8081-exec-7] c.a.s.d.controller.DeliveryController    : Delivery Service Triggered for OrderId 200
      [delivery] [nio-8081-exec-7] c.a.s.d.service.DeliveryServiceImpl      : Calling Payment Service for OrderId 200
      [paymentService] [nio-8082-exec-4] c.a.s.p.service.PaymentServiceImpl       : Payment for OrderId received 200
      [paymentService] [nio-8082-exec-4] c.a.s.p.service.PaymentServiceImpl       : Reverting OrderId 200 due to payment being greater than 2000
      [delivery] [nio-8081-exec-4] c.a.s.d.controller.DeliveryController    : Delivery orderId 200 reverse request received
      [delivery] [nio-8081-exec-4] c.a.s.d.service.DeliveryServiceImpl      : Delivery of the OrderId null reverted
      [order] [nio-8080-exec-9] c.a.s.o.service.OrderServiceImpl         : Reverting OrderId 200

