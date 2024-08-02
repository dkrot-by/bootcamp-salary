FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package

FROM openjdk:17
COPY --from=build /app/target/bootcamp-salary*.jar /usr/local/lib/bootcamp-salary.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/bootcamp-salary.jar"]