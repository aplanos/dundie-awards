# Auth Module - Dundie Awards

This repository contains the **Auth Module** for the **Dundie Awards** project.

## Requirements

In order for this module to run, it is essential to first start the organization-module, which boots up the database, Redis, and RabbitMQ servers. In the future, a separate module will be developed to manage the infrastructure setup, eliminating the dependency on the organization-module for this purpose.

## Features

- API for microservices authentication
- Security configuration for token validation using JWT
- Swagger configured for API documentation

## API Documentation

Swagger has been configured to document the API endpoints. You can access the API documentation at:

```
http://localhost:8082/swagger-ui.html
```

## Project Structure

The project follows a modular structure with the following main packages:

```
com.ninjaone.dundieawards.auth
│── application
│   ├── api
│   ├── dto
│   ├── service
│── domain
│   ├── entity
│── infrastructure
│   ├── config
│   ├── redis
│   ├── repository
│   ├── rest
│   ├── security
│── AuthModuleApplication
│── resources
│   ├── application.properties
│   ├── application-dev.properties
│   ├── application-prd.properties
│── test
├── build.gradle
├── def.Dockerfile
├── prd.Dockerfile
```

## Prerequisites

To run this project, ensure you have the following installed:

- **Java 17**: The project requires Java 17 to run. You can download it from the [official site](https://adoptopenjdk.net/).
- **Gradle**: Gradle is used as the build tool for this project. You can download it from the [official site](https://gradle.org/install/).
- **Docker**: Docker is required for containerization. You can download it from the [official site](https://www.docker.com/products/docker-desktop).

Ensure that these tools are properly installed and configured before running the project.

## Build & Run

To build and run the project, use the following commands:

```sh
./gradlew build
./gradlew bootRun
```

## Deployment

Docker support is available with predefined Dockerfiles:

- `def.Dockerfile` - Default configuration
- `prd.Dockerfile` - Production configuration

