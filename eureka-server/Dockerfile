FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY . .
RUN mvn -q -DskipTests -pl eureka-server -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
