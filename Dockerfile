FROM maven:3.5-jdk-8-alpine AS build

WORKDIR /code

COPY pom.xml /code/pom.xml
#RUN ["mvn", "dependency:resolve"]
#RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
COPY ["./", "/code/"]
RUN ["mvn", "package"]

#FROM openjdk:8-jre-alpine
FROM openjdk:8-jdk


COPY --from=build /code/fs-service/target/fs-service-0.0.1-SNAPSHOT.jar /

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/fs-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
