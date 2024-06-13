FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar /app/orderservice.jar
EXPOSE 8082
CMD ["java", "-jar", "orderservice.jar"]