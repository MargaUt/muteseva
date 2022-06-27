# Kaip naudotis programa

#### Technologies used: 
- React 17.0.2,  Boostrap 5.1.3
- Spring Boot 2.6.3, Java 11
- Spring security
- H2 database
- Apache Tomcat 9.0.58 server
- Swagger-UI, Maven

## Getting Started

- Clone the repository `git clone [https://github.com/MargaUt/Slytherin.git](https://github.com/MargaUt/muteseva.git)`

### Run on Tomcat Server

- go to project folder `cd .../Projektas_Darzeliu_IS/back`
- run the application on Tomcat Server (port 8081):
  
```
 mvn clean install org.codehaus.cargo:cargo-maven2-plugin:1.7.7:run -Dcargo.maven.containerId=tomcat9x -Dcargo.servlet.port=8081 -Dcargo.maven.containerUrl=https://repo1.maven.org/maven2/org/apache/tomcat/tomcat/9.0.40/tomcat-9.0.40.zip
 ```
 - the application will start on your browser http://localhost:8081/darzelis

### Run with Spring boot and npm/yarn

- go to project folder `cd .../Projektas_Darzeliu_IS/back`
- Run `mvn spring-boot:run` (application will start on port 8080)
- go to project folder `cd .../Projektas_Darzeliu_IS/front`
- run `npm install` or `yarn install`
- open file `..\Projektas_Darzeliu_IS\front\src\components\10Services\endpoint.js`
- change `const apiEndpoint= process.env.PUBLIC_URL` to `const apiEndpoint = "http://localhost:8080"`
- run `npm run start` or `yarn start`
- application will open on your browser at http://localhost:3000

### Accessing the database

http://localhost:8081/darzelis/console

```
JDBC URL:jdbc:h2:file:~/home/h2/slytherin.db
User Name:sa
Password:

```

### Accessing API documentation 

http://localhost:8081/darzelis/swagger-ui/


## Running the tests

- for smoke tests, run smoke.xml
- for regression tests, run regression.xml

### Break down into end to end tests

There are 7 different test packages: adminTests, specialistTests, parentTests, login, smokeTests, generalMethods and basetest. First 3 are the main ones, generalMethods holds reusable code for different test cases and basetest is for set up (getting Chrome driver and application link) and closing all tests after running them.

```
adminTests package tests:
- create and delete new user (all three roles)
- update admin details (change user information, password, reset password)

specialistTests package tests:
- check if throws error message
- check the application for compensation appearance
- create and delete new kindergarten
- download parent document PDF
- update specialist details (change user information, password, reset password)
  
parentPages package tests:
- generate and download document
- submit and delete application for compensation
- submit and delete new application
- update parent details (change user information, password, reset password)
- upload medical document (pdf)
```

## Deployment

To make a war file for deployment:
- run `mvn clean install` while in the project folder `.../Projektas_Darzeliu_IS/back`
- `darzelis.war` file will appear in the `..\Projektas_Darzeliu_IS\back\target` folder

## Authors

List of [contributors](https://github.com/MargaUt/Slytherin/graphs/contributors) who participated in this project.

Copyright ©️ 2022, Slytherin
