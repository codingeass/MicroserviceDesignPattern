FROM openjdk:21-jdk-slim

VOLUME /tmp

COPY target/orderService-0.0.1-SNAPSHOT.jar orderService.jar

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006", "-jar", "/orderService.jar"]