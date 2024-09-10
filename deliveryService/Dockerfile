FROM openjdk:21-jdk-slim

VOLUME /tmp

COPY target/deliveryService-0.0.2.jar deliveryService-1.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/deliveryService-1.jar"]