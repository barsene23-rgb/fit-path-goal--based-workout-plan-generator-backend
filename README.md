# FitPath Goal-Based Workout Plan Generator – Backend

- **Full name:** Byusa Arsene
- **Student ID:** 26164
- **Project:** FitPath Goal-Based Workout Plan Generator
- **Course:** INSY 8322 – Web Technology and Internet (AUCA)

## Stack
Spring Boot 3, Spring Data JPA, PostgreSQL, Java 17, Maven.

## Running the API
1. Install Java 17+ and PostgreSQL.
2. Create the database: `CREATE DATABASE fitpath_db;`
3. Check the credentials in `src/main/resources/application.properties`.
4. Start the app: `./mvnw spring-boot:run` (or `mvn spring-boot:run`, or run `FitPathApplication` from your IDE).
5. The API is available at `http://localhost:8080`; Hibernate creates the tables on first start.
6. (Optional) Load the sample data with `seed.sql`, then import
   `fit-path-goal--based-workout-plan-generator-midterm.postman_collection.json` into Postman.

## Endpoints
One Postman request per requirement, named by its ID (A2, B1 … F2, Bonus 1, Bonus 2).
