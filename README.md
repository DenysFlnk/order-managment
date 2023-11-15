# Order manager app for translation agency 
> Order manager app for abstract translation agency created with Spring Boot, PostgreSQL, JavaScript(jQuery) and SendGrid as email service.

## Table of Contents
* [General purpose](#general-purpose)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Future goals](#future-goals)
* [Contact](#contact)

## General purpose
To develop multifunctional application for translation agency. A lot of agencies using plain Microsoft Office or/and Google services to manage its orders from clients and communicate with translators. 
The idea is to create app that can automize agency daily routine and improve quality of work.


## Technologies Used
- Java 17
- Spring Boot 3.1.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- SendGrid
- Docker
- Logback logging
- Testing:
  - JUnit 5
  - Mockito
  - Spring Security Test 6.1.0


## Features
App features:
- Form-based Authentication
- Data binding, validation
- Global exception handling both app and security
- Logging
- Tests for app logic and security
- Creating and sending email to translator

## Setup
1. Install [Docker](https://www.docker.com/).
2. Create env files:
````
#app.env
SPRING_PROFILES_ACTIVE=deploy
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/#your db name 
SPRING_DATASOURCE_USERNAME=#your username
SPRING_DATASOURCE_PASSWORD=#your password

#db.env
POSTGRES_USER=#your username
POSTGRES_PASSWORD=#your password
POSTGRES_DB=#your db name
````
3. Run command ```docker-compose up -d``` in project directory.
4. Follow [app link](http://localhost:8080/translation-agency/login-form).
5. Enjoy!

## Future goals
- Internationalization:
  - Ukrainian (done)
- Sorting for orders
- Statistics by month for orders
- Export reports in pdf

## Contact
Created by Denys Filonenko:
- [GitHub](https://github.com/DenysFlnk)
- [Email](mailto:filonenko.denys94@gmail.com)
