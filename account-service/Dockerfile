FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY . .
RUN mvn -q -DskipTests -pl account-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/account-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
