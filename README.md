# Eureka! Clinical User Webapp
User interface and web client proxy for managing user accounts

## Version 1.0 development series
Latest release: [![Latest release](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-user-webapp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-user-webapp)

## Version history
No final releases yet

## What does it do?
It provides web pages for users to manage their user profiles and change their password. It also implements a proxy servlet and router for web clients to access the web services provided by eurekaclinical-user-service.

## Building it
The project uses the maven build tool. Typically, you build it by invoking `mvn clean install` at the command line. For simple file changes, not additions or deletions, you can usually use `mvn install`. See https://github.com/eurekaclinical/dev-wiki/wiki/Building-Eureka!-Clinical-projects for more details.

## Performing system tests
You can run this project in an embedded tomcat by executing `mvn tomcat7:run -Ptomcat` after you have built it. It will be accessible in your web browser at https://localhost:8443/eurekaclinical-user-webapp/. Your username will be `superuser`.

## Maven dependency
```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-user-webapp</artifactId>
    <version>version</version>
</dependency>
```

## Developer documentation
* [Javadoc for latest development release](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-user-webapp) [![Javadocs](http://javadoc.io/badge/org.eurekaclinical/eurekaclinical-user-webapp.svg)](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-user-webapp)

## Getting help
Feel free to contact us at help@eurekaclinical.org.

