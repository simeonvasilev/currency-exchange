FROM maven:3.8.6-eclipse-temurin-17

COPY target/currency-exchange.jar currency-exchange.jar

ENTRYPOINT ["java","-jar","/currency-exchange.jar"]