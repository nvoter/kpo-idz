FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar /app/authservice.jar
EXPOSE 8080
CMD ["java", "-jar", "authservice.jar"]