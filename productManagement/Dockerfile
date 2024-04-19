FROM maven:3.9.0-amazoncorretto-17
LABEL authors="edgar.ramirez"

COPY . /productManagement
WORKDIR /productManagement

CMD ["java", "-jar", "./target/productManagement-2.0-SNAPSHOT.jar"]