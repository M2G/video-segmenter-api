# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY Makefile .
COPY Makefile.local .

RUN apt-get update \
 && apt-get install -y make \
 && rm -rf /var/lib/apt/lists/*

RUN make deps
RUN make build

# ---- Runtime stage ----
FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]