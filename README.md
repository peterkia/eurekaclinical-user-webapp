# Eureka! Clinical User Webapp
User interface and web client proxy for managing user accounts

# Version history
No final releases yet

# What does it do?
It provides JSP pages for users to manage their user profiles and change their password. It also implements a proxy servlet and router for web clients to access the web services provided by eurekaclinical-user-service.

# Building it
The project uses the maven build tool. Typically, you build it by invoking `mvn clean install` at the command line. For simple file changes, not additions or deletions, you can usually use `mvn install`.

# Performing system tests
You can run this project in an embedded tomcat by executing `mvn tomcat7:run -Ptomcat` after you have built it. It will be accessible in your web browser at URL https://localhost:8443/eurekaclinical-user-webapp/.
