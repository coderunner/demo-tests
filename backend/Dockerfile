FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
ARG FIREBASE_KEY=firebase-key.json
COPY ${JAR_FILE} app.jar
COPY ${FIREBASE_KEY} firebase-key.json
ENTRYPOINT ["java","-jar","/app.jar"]