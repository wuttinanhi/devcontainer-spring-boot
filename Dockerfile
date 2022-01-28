FROM openjdk:11-slim-buster as base
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

FROM base as build
COPY . .
RUN ./mvnw package

FROM openjdk:11-slim-buster
COPY --from=build target/demo-0.0.1-SNAPSHOT.jar springbootdevcontainer.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar springbootdevcontainer.jar
