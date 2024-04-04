FROM openjdk:17-oracle

COPY .aws/ root/.aws

WORKDIR /diva/back

COPY ./backend-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","backend-0.0.1-SNAPSHOT.jar"]