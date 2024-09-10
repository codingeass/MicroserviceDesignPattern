FROM openjdk:21-jdk-slim

VOLUME /tmp

COPY target/paymentService-0.0.1-SNAPSHOT.jar paymentService.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007", "-jar", "/paymentService.jar"]