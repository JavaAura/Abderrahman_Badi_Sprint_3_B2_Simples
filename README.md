# Simples API

A REST API for digitalizing the management of training programs, learners, trainers, and training sessions at a training center, developed using Java and Spring Boot

## Table of Contents

- [Project Overview](#project-overview)
- [Installation](#installation)
- [Structure](#structure)
- [Features](#features)
- [Technologies](#technologies)


## Project Overview

**Context**:  
The Training Management API is designed for a training center to manage training programs digitally. It allows for the management of learners, trainers, classes, and training sessions through a RESTful interface.

**Objectives**:
- Implementing a management system for `students`, `trainers`, `classrooms`, and training `programs`.
- The use Spring Boot for creating a robust REST API with proper endpoint management.
- Utilizing Spring Data JPA for data access and repository management.
- Implement validation and exception handling for API requests.
- Document the API using Swagger.

## Installation

### Prerequisites

- Java 8 or higher
- Apache Maven
- PostgreSQL Server

### Steps

1. **Clone the repository:**

   ```sh
   git clone https://github.com/Yorften/Simples.git
   cd Simples/simples

2. **Build the application:**
   ```sh
   mvn clean install

3. **Run the application:**
   ```sh
   mvn spring-boot:run

## Structure

- **Entities**:  
  Defines JPA entities such as `User`, `Trainer`, `Student`, ect... and their relationships using JPA.

- **Repository Layer**:  
  Extend `JpaRepository` for data access and include custom query methods.

- **Service Layer**:  
  Contains business logic and orchestrates operations between the Controller and Repository layers.
  
- **Controller Layer**:  
  Implements REST endpoints for managing learners, trainers, classes, and training sessions using `@RestController` annotation.
  
- **Exceptions**:  
  Centralized exception handling for REST API responses.

- **Utilities**:  
  Common utility classes and methods.

- **Tests**:  
  Integration tests implemented with JUnit.

## Features

1. **Student Management**:
   - Register, update, delete, and view students details.

2. **Trainer Management**:
   - Create, update, add/remove players, and view trainers details.

3. **Program Management**:
   - Create, update, add/remove teams, and view programs details.

4. **Classroom Management**:
   - Register, update, delete, and view class rooms details.

5. **API Documentation**:
   - Automatic documentation of API endpoints using Swagger.


## Technologies

- **Java 8**: Core language used for development.
- **Apache Maven**: For dependency management and project build.
- **Spring Boot**: For creating the REST API and managing application configuration.
- **Spring Data JPA**: For database interactions and repository management.
- **PostgreSql**: Relational database for storing data.
- **H2 Database**: In-memory database for development.
- **Hibernate**: ORM for database access and management.
- **JUnit**: For unit testing.
- **JaCoCo**: For code coverage.
- **Lombok**: For reducing boilerplate code.
- **Swagger**: For API documentation.