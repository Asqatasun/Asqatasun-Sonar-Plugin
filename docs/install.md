# Installation of Tanaguru SonarQube plugin

## Prerequesites

* SonarQube 3.7.4+ installed (Have a look to the [official *Installing SonarQube* page](http://docs.sonarqube.org/display/SONAR/Installing) for details about installation and usage)
* SonarRunner 2.4+ installed (Have a look to the [official *Installing and Configuring SonarQube Runner* page](http://docs.sonarqube.org/display/SONAR/Installing+and+Configuring+SonarQube+Runner) for details about installation)

## Installation

By now, unfortunately, the Tanaguru sonar plugin is not accessible from the available plugin page of SonarQube.

The installation of the plugin needs to be done "manually" by copying the jar of the plugin in the extensions/plugins folder of the local sonar context.

Let's say your SonarQube is installed in the '/opt/sonar' folder. Execute the following command : 

```bash
cd /tmp/
wget http://download.tanaguru.org/Tanaguru-sonar-plugin/sonar-tanaguru-latest.jar
sudo mv sonar-tanaguru* /opt/sonar/extensions/plugins/
```

and restart the service.

The plugin is now installed and a new language, called **accessibility**, is available for analysis.

## Next step

proceed to [Configuration](configuration.md)
