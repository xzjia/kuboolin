FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/kuboolin-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar