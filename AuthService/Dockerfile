FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar /app/authservice.jar
CMD ["java", "-jar", "authservice.jar"]