FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN echo "============== LISTING SRC FILES =============="
RUN ls -R /app/src
RUN echo "==============================================="

CMD ["mvn", "spring-boot:run"]