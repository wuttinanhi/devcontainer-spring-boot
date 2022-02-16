# Default environment variable
ARG JWT_SECRET_DEFAULT="JWT_SECRET"
ARG JWT_DURATION_DEFAULT="300000"
ARG JWT_ISSUER_DEFAULT="devcontainer-spring-boot"

# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.209.6/containers/java/.devcontainer/base.Dockerfile
# [Choice] Java version (use -bullseye variants on local arm64/Apple Silicon): 11, 17, 11-bullseye, 17-bullseye, 11-buster, 17-buster
ARG VARIANT="11"
FROM mcr.microsoft.com/vscode/devcontainers/java:0-${VARIANT} as development_stage
# development username
ARG DEV_USERNAME="vscode"

# [Option] Install Maven
ARG INSTALL_MAVEN="true"
ARG MAVEN_VERSION=""
# [Option] Install Gradle
ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# [Choice] Node.js version: none, lts/*, 16, 14, 12, 10
ARG NODE_VERSION="none"
RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# Environment variable
ENV ENVIRONMENT "development"

ARG JWT_SECRET_DEFAULT
ENV JWT_SECRET ${JWT_SECRET_DEFAULT}

ARG JWT_DURATION_DEFAULT
ENV JWT_DURATION ${JWT_DURATION_DEFAULT}

ARG JWT_ISSUER_DEFAULT
ENV JWT_ISSUER ${JWT_ISSUER_DEFAULT}

RUN mkdir -p /home/$DEV_USERNAME/.vscode-server/extensions /home/$DEV_USERNAME/.vscode-server-insiders/extensions \
    && chown -R $DEV_USERNAME home/$DEV_USERNAME/.vscode-server /home/$DEV_USERNAME/.vscode-server-insiders

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment this line to install global node packages.
# RUN su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g <your-package-here>" 2>&1

# change user to vscode
USER vscode



FROM openjdk:11-slim-buster as production_base
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

FROM production_base as production_build
COPY . .
RUN ./mvnw package

FROM openjdk:11-slim-buster
# Environment variable
ENV ENVIRONMENT "production"

ARG JWT_SECRET_DEFAULT
ENV JWT_SECRET ${JWT_SECRET_DEFAULT}

ARG JWT_DURATION_DEFAULT
ENV JWT_DURATION ${JWT_DURATION_DEFAULT}

ARG JWT_ISSUER_DEFAULT
ENV JWT_ISSUER ${JWT_ISSUER_DEFAULT}

COPY --from=production_build target/demo-0.0.1-SNAPSHOT.jar springbootdevcontainer.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar springbootdevcontainer.jar
