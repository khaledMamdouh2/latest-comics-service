FROM openjdk:11
ADD target/latest-comics-service.jar latest-comics-service.jar
EXPOSE 8080
ENTRYPOINT ["java" , "-jar", "latest-comics-service.jar"]