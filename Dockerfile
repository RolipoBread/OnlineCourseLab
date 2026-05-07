# =========================
# 1. BUILD FRONTEND
# =========================
FROM node:20 AS frontend-build

WORKDIR /frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend ./
RUN npm run build


# =========================
# 2. BUILD BACKEND
# =========================
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


# =========================
# 3. FINAL IMAGE
# =========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# backend jar
COPY --from=backend-build /app/target/*.jar app.jar

# frontend build -> Spring static (если используешь)
COPY --from=frontend-build /frontend/dist ./static

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]