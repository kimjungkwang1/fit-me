FROM openjdk:17-oracle

COPY .aws/ root/.aws

WORKDIR /diva/batch

COPY ./batch-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","batch-0.0.1-SNAPSHOT.jar"]