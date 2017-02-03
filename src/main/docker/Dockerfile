FROM openjdk:8-jre-alpine

MAINTAINER David Q. Romney

ENV SPRING_BATCH_EVN=dev

VOLUME ["/tmp"]

# Directory for external configuration files
VOLUME ["/config"]

ADD @project.build.finalName@.jar @project.build.finalName@.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/@project.build.finalName@.jar"]
