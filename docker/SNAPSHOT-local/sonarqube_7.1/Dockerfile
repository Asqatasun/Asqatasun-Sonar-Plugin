FROM sonarqube:7.1

# #### usage ######################################################
#
#   --- Building this docker image
#   docker build -t asqa_sonarqube_7.1 .
#
#   --- Launch a container
#   docker run -d --name asqa.sonar -p 9000:9000 -p 9092:9092 asqa_sonarqube_7.1
#
#   --- Playing with this docker image
#   docker exec -ti asqa.sonar /bin/bash
#   docker logs -f  asqa.sonar
#
###################################################################
#  https://hub.docker.com/r/library/sonarqube/
#  https://github.com/SonarSource/docker-sonarqube/blob/master/7.1/Dockerfile
###################################################################


# environment variables
ENV PLUGIN_VERSION="0.2.1-SNAPSHOT"

    # in "sonarqube:7.1" DockerFile
    #     ENV SONARQUBE_HOME=/opt/sonarqube

# Add sonarqube plugin (.jar)
ADD sonar-asqatasun-${PLUGIN_VERSION}.jar  ${SONARQUBE_HOME}/extensions/plugins
RUN chown -R sonarqube:sonarqube           ${SONARQUBE_HOME}/extensions/plugins

    # in "sonarqube:7.1" DockerFile
    #     ENTRYPOINT ["./bin/run.sh"]
    
