FROM eclipse-temurin:21.0.4_7-jdk
ARG JAR_FILE=target/ExpressLane-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_ExpressLane.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app_ExpressLane.jar"]