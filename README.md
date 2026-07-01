# Food Order DevOps Project

A Spring Boot–based REST API for restaurant food order management, containerized with Docker and automated through a Jenkins CI/CD pipeline. This project demonstrates modern DevOps practices for building, testing, and deploying a Java web application.

## Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.2.5
- **Build Tool:** Maven 3.9.6
- **Database:** MySQL
- **Container:** Docker (multi-stage build)
- **CI/CD:** Jenkins
- **Notable Libraries:**
  - Spring Boot Starter Web (REST API)
  - Spring Data JPA (ORM)
  - MySQL Connector/J (database driver)
  - SpringDoc OpenAPI (Swagger UI for API documentation)

## How it's organized

```
src/
  main/
    java/com/restaurant/foodorder/
      FoodorderApplication.java    Spring Boot entry point
      controller/                  REST endpoints and request handling
      model/                       Entity classes for data models
      repository/                  JPA repositories for data access
    resources/                     Configuration files and static assets
  test/                           Unit and integration tests
pom.xml                           Maven project configuration
Dockerfile                        Multi-stage Docker build
Jenkinsfile                       CI/CD pipeline definition
.gitignore                        Git ignore rules
```


**How it fits together:** Requests come into the REST controllers in the `controller/` package, which delegate to business logic that interacts with `model/` entities. Data access is handled through Spring Data JPA repositories connected to MySQL. The entire application is built as a single JAR archive (`foodorder-0.0.1-SNAPSHOT.jar`) and packaged into a Docker image for consistent deployment.

## How to run it

### Prerequisites
- Java 17+
- Maven 3.9.6+
- Docker & Docker Compose
- MySQL (or use Docker)
- Jenkins (for CI/CD pipeline)

### Local Development

```bash
# Clone the repository
git clone https://github.com/Sam-Immortal/foodorder-devops-project.git
cd foodorder-devops-project

# Build and run tests
mvn clean test

# Package the application
mvn package -DskipTests

# Run the Spring Boot application
mvn spring-boot:run

```

The API will be available at `http://localhost:8080` with Swagger UI documentation at `http://localhost:8080/swagger-ui.html`.

### Docker Deployment

```bash

# Build the Docker image
docker build -t foodorder-app:latest .

# Run the container
docker run -d -p 8080:8080 --name food-server foodorder-app:latest

```

The application will be accessible at `http://localhost:8080`.

### Jenkins Pipeline

The `Jenkinsfile` automates the entire build and deployment process:

1. **Checkout** – Pulls code from Git
2. **Build & Test** – Runs `mvn clean test`
3. **Package** – Creates the JAR file
4. **Docker Build** – Builds the Docker image as `foodorder-app:latest`
5. **Deploy Local Server** – Stops any existing container and starts a fresh one

The pipeline runs on Windows agents (uses `bat` commands). Trigger a build in Jenkins to execute the full pipeline.
