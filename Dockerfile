# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY application-prod.yml ./src/main/resources/
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/application-prod.yml ./application-prod.yml

# 환경 변수 설정
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:./application-prod.yml"]
