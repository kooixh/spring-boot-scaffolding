# Spring Boot Scaffolding

### Prerequisites

- Java 1.8
- Maven 3+
- Local SonarQube instance
	
### Local Envrionment setup

#### SonarQube
- Download and unzip the `sonarqube` [from this link](https://www.sonarqube.org/downloads/)
- Unzip and `cd` to the sonarqube app directory.
- Run `./bin/<YOUR_OS>/sonar.sh start` 

### Running The App

A Makefile is included to make life easier during development. Use these available commands to start developement.

#### Commands

**Running tests:** `mvn clean test`

**Package into a `jar`:** `make build_common` or `mvn clean package`

**Running the app:** `make run` or `mvn spring-boot:run -Dspring-boot.run.profiles=local`

**Running SonarQube scan:** `make sonar` or `mvn sonar:sonar`

### Other References

##### Tools & Techs Included

This is a Spring Boot project with `maven` as build and package manager. The related techs in the project
is listed below with proper reference:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#production-ready)

##### Basics

If you are new to Spring Boot and other related techs, consider going through the tutorials given below:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
