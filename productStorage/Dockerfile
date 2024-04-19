FROM maven:3.9.0-amazoncorretto-17
LABEL authors="edgar.ramirez"

COPY . /productStorage
WORKDIR /productStorage

CMD ["java", "-jar", "./target/productStorage-2.0-SNAPSHOT.jar"]