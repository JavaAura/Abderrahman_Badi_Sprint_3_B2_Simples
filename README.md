# Wolf Esports Tournament Manager

A console and web application for managing players, teams, and tournaments in the e-sport domain, developed using Java, Spring Core, and Hibernate.

## Table of Contents

- [Project Overview](#project-overview)
- [Installation](#installation)
- [Structure](#structure)
- [Features](#features)
- [Technologies](#technologies)


## Project Overview

**Context**:  
Wolf Esports is designed for an e-sport organization to help organize and monitor video game tournaments. The application manages players, teams, and tournaments efficiently, providing both basic and advanced tournament duration estimations.

**Objectives**:
- Implement player, team, and tournament management.
- Use Spring Core for Dependency Injection (DI) via XML configuration `applicationContext.xml`.
- Implement CRUD operations for `players`, `teams`, and `tournaments`.
- Apply a layered architecture (MVC) to separate presentation, business, and persistence logic.
- Demonstrate the Open/Closed principle by extending the `TournamentDao` interface.

## Installation

### Prerequisites

- Java 8 or higher
- Apache Maven
- MySQL Server

### Setup your database:

1. The application automatically creates the required tables in the H2 database when it runs, as configured in `persistence.xml`.

### Setup environment variable

1. **For windows:**
   ```cmd
   set DB_URL= "database url"
   set DB_USER= "username"
   set DB_PASSWORD = "password"

2. **For linux based:**
   ```bash
   export DB_URL= "database url"
   export DB_USER= "username"
   export DB_PASSWORD = "password"


### Steps

1. **Clone the repository:**

   ```sh
   git clone https://https://github.com/Yorften/WolfEsports.git
   cd WolfEsports/wolfesports

2. **Build the application:**
   ```sh
   mvn clean install

3. **Compile the application:**
   ```sh
   mvn package

4. **Deploy:**

- Deploy the WAR file in Apache Tomcat by copying the WAR from the /target folder to Tomcat's webapps directory.
- Start the Tomcat server and access the application in your browser at http://localhost:8080/WolfEsports.

5. **Run with Eclipse/IntelliJ IDEA (optional)**

- Open the project in your ide.
- Build maven dependencies.
- Run the app or server.

## Structure

- **Model Layer**:  
  Defines entities such as `Player`, `Team`, `Tournament`, and `Game` and their relationships using JPA.
  
- **Controller Layer**:  
  Handles user interactions, receives input from the UI, and communicates with the Service layer to process the requests.
  
- **Repository Layer**:  
  Provides an abstraction for data access. This layer interacts with the database using JDBC and Hibernate ORM to handles CRUD operations.
  
- **Service Layer**:  
  Contains business logic and orchestrates operations between the Controller and Repository layers.
  
- **Presentation Layer**:  
  Thymeleaf templates are used to create dynamic and reusable layouts.

## Features

1. **Player Management**:
   - Register, update, delete, and view player details.

2. **Team Management**:
   - Create, update, add/remove players, and view team details.

3. **Tournament Management**:
   - Create, update, add/remove teams, and view tournament details.
   - Calculate the estimated duration of tournaments (basic and advanced).

4. **Game Management**:
   - Create, update, add/remove games.

5. **Advanced Features**:
   - Status tracking for orders (On hold, Processing, Shipped, etc..).
   - Pagination and search functionality for large datasets.


## Technologies

- **Java 8**: Core language used for development.
- **Spring Core**: For Dependency Injection (DI) via XML configuration.
- **Apache Maven**: For dependency management and project build.
- **MySQL**: Relational database for storing data.
- **H2 Database**: In-memory database for development.
- **Hibernate**: ORM for database access and management.
- **Apache Tomcat**: Web server for deploying the application.
- **JUnit**: For unit testing.
- **Mockito**: For mocking classes to unit test.
- **JaCoCo**: For code coverage.
- **Tailwind CSS**: For responsive UI design.
- **JIRA**: For project management using Scrum methodology.
- **Git**: For version control with branches.