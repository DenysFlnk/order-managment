FROM eclipse-temurin:17

WORKDIR /app

EXPOSE 8080

ARG JAR_FILE=target/order-manager-1.0.jar

ARG WEB_APP=src/main/webapp/

COPY $JAR_FILE order-manager-app.jar

COPY $WEB_APP src/main/webapp/

ENTRYPOINT ["java", "-jar", "order-manager-app.jar"]


