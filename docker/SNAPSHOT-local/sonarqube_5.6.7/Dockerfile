FROM openjdk:8

# #### usage ######################################################
#
#   --- Building this docker image
#   docker build -t asqa_sonarqube_5.6.7 .
#
#   --- Launch a container
#   docker run -d --name asqa.sonar -p 9000:9000 -p 9092:9092 asqa_sonarqube_5.6.7
#
#   --- Playing with this docker image
#   docker exec -ti asqa.sonar /bin/bash
#   docker logs -f  asqa.sonar
#
###################################################################
#  https://hub.docker.com/r/library/sonarqube/
#  https://github.com/SonarSource/docker-sonarqube/blob/master/5.6.7/Dockerfile
###################################################################

ENV SONAR_VERSION=5.6.7 \
    SONARQUBE_HOME=/opt/sonarqube \
    # Database configuration
    # Defaults to using H2
    SONARQUBE_JDBC_USERNAME=sonar \
    SONARQUBE_JDBC_PASSWORD=sonar \
    SONARQUBE_JDBC_URL=

# Http port
EXPOSE 9000

RUN groupadd -r sonarqube && useradd -r -g sonarqube sonarqube

# grab gosu for easy step-down from root
RUN set -x \
    && wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/1.10/gosu-$(dpkg --print-architecture)" \
    && wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/1.10/gosu-$(dpkg --print-architecture).asc" \
    && export GNUPGHOME="$(mktemp -d)" \
    && gpg --keyserver ipv4.pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4 \
    && gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu \
    && rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc \
    && chmod +x /usr/local/bin/gosu \
    && gosu nobody true

RUN set -x \

    # pub   2048R/D26468DE 2015-05-25
    #       Key fingerprint = F118 2E81 C792 9289 21DB  CAB4 CFCA 4A29 D264 68DE
    # uid                  sonarsource_deployer (Sonarsource Deployer) <infra@sonarsource.com>
    # sub   2048R/06855C1D 2015-05-25
    && gpg --keyserver ipv4.pool.sks-keyservers.net --recv-keys F1182E81C792928921DBCAB4CFCA4A29D26468DE \

    && cd /opt \
    && curl -o sonarqube.zip -fSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip \
    && curl -o sonarqube.zip.asc -fSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip.asc \
    && gpg --batch --verify sonarqube.zip.asc sonarqube.zip \
    && unzip sonarqube.zip \
    && mv sonarqube-$SONAR_VERSION sonarqube \
    && chown -R sonarqube:sonarqube sonarqube \
    && rm sonarqube.zip* \
    && rm -rf $SONARQUBE_HOME/bin/*


###################################################################
# environment variables
ENV PLUGIN_VERSION="0.2.1-SNAPSHOT"

# Add sonarqube plugin (.jar)
ADD sonar-asqatasun-${PLUGIN_VERSION}.jar  ${SONARQUBE_HOME}/extensions/plugins
RUN chown -R sonarqube:sonarqube           ${SONARQUBE_HOME}/extensions/plugins
###################################################################

VOLUME "$SONARQUBE_HOME/data"

WORKDIR $SONARQUBE_HOME
COPY run.sh $SONARQUBE_HOME/bin/
ENTRYPOINT ["./bin/run.sh"]

