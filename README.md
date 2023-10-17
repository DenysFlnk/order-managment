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
To develop multifunctional application for any translation agency. A lot of agencies using plain Microsoft Office or/and Google services to manage its orders from clients and communicate with translators. 
The idea is to create app that can automize agency daily routine and improve quality of work.


## Technologies Used
- Java 17
- Spring Boot 3.1.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- SendGrid
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
Create db in PostgreSQL named "translation_agency" with credentials:
>username: postgres
> 
>password: userPassword

Fork this repo to your GitHub and clone to your PC. Run `OrderManagerApplication` in your IDE. 

## Future goals
- Internationalization:
  - Ukrainian (in progress)
- Sorting for orders
- Statistics by month for orders
- Export reports in pdf

## Contact
Created by Denys Filonenko:
- [GitHub](https://github.com/DenysFlnk)
- [Email](mailto:filonenko.denys94@gmail.com)
