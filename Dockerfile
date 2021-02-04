FROM openjdk:latest
EXPOSE 8083
COPY target/*.jar medical-records-docker.jar
ENTRYPOINT ["java","-jar","medical-records-docker.jar"]