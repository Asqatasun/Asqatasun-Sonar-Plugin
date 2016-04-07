# Installation of Asqatasun SonarQube plugin

## Prerequesites

* SonarQube 3.7.4+ installed (Have a look to the [official *Installing SonarQube* page](http://docs.sonarqube.org/display/SONAR/Installing) for details about installation and usage)
* SonarRunner 2.4+ installed (Have a look to the [official *Installing and Configuring SonarQube Runner* page](http://docs.sonarqube.org/display/SONAR/Installing+and+Configuring+SonarQube+Runner) for details about installation)

## Installation

The installation of the plugin needs to be done "manually" by copying the jar of the plugin in the extensions/plugins folder of the local sonar context.

Let's say your SonarQube is installed in the '/opt/sonar' folder. Execute the following command : 

@@@TODO fix URL http://download.asqatasun.org/Asqatasun-sonar-plugin/sonar-asqatasun-latest.jar

```bash
cd /tmp/
wget http://download.asqatasun.org/Asqatasun-sonar-plugin/sonar-asqatasun-latest.jar
sudo mv sonar-tanaguru* /opt/sonar/extensions/plugins/
```



and restart the service.

The plugin is now installed and a new language, called **accessibility**, is available for analysis.

(By now, the Asqatasun sonar plugin is not accessible from the available plugin page of SonarQube.)

## Next step

proceed to [Configuration](configuration.md)
