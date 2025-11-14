# ============================
# 1) Build stage
# ============================
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests package

RUN ./mvnw dependency:resolve
RUN ./mvnw -q -DskipTests package

# ============================
# 2) Runtime stage
# ============================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
