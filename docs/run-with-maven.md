# Run Tanaguru analysis with Maven

You can have all the details about usage and set-up in the [official *Analyzing with Maven* page](http://docs.sonarqube.org/display/SONAR/Analyzing+with+Maven).

## Concrete example

### General configuration from settings.xml

Edit the settings.xml file, located in $MAVEN_HOME/conf or ~/.m2, to set the "accessibility" language as the default language used for sonar analysis, and eventually override the source dir location

```xml
<settings>
    <profiles>
        <profile>
            <id>sonar</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
               <sonar.language>accessibility</sonar.language>
            </properties>
        </profile>
     </profiles>
</settings>
```

Then the analysis can be launched from the command :

```
mvn sonar:sonar
```

This configuration overrides the default behaviour that performs analysis on java source code.

To keep the default behaviour, you just need to set an id different from "*sonar*" to the profile. Let's replace "*sonar*" by "*tanaguru*" in the previous settings example.

The following command  will perform analysis with "Java way" profile : 

```
mvn sonar:sonar
```

The following command will perform analysis with "Tanaguru" profile, i.e accessibility language : 

```
mvn sonar:sonar -Ptanaguru
```

By default the *sonar.sources* property is set to the value of the Maven sourceDirectory property (by default it is src/main/java) plus pom.xml (and also src/main/webapp is automatically added for war modules).

Regarding the type of your project, you may wish to override this by using the "*sonar.sources*" property that can be defined in the profile settings.

### Configuration at project level from pom.xml

Profiles can be defined at project level. They can be set in the pom.xml directly. Thus, a *tanaguru* profile may look like : 
```xml
...
    <profiles>
        <profile>
            <id>tanaguru</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
               <sonar.language>accessibility</sonar.language>
            </properties>
        </profile>
     </profiles>
...
```



## Next step

* proceed to [Run with Maven](run-with-sonar-runner.md)
* proceed to [Run with SonarQube Jenkins plugin](run-with-jenkins.md)
* proceed to [Run with SonarQube Eclipse plugin](run-with-eclipse.md)
