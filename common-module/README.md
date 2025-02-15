# Activity Module

The Dundie Awards Common Module is a Java-based Spring Boot library with shared feature that can be used commonly in all the other modules of the project.

## Features

- Mapper Utils using ModelMapper
- Json Utils using Jackson
- Security classes responsible for connecting to the shared secrets server and provide the JWT token keys or others parameter required.

## Project Structure

The project follows a modular structure with the following main packages:

```
com.ninjaone.dundieawards.common
│── application
│── domain
│   ├── services
│   ├── specification
│── infrastructure
│   ├── config
│   ├── security
│   ├── utils
│── resources
│   ├── application.properties
│   ├── application-dev.properties
│   ├── application-prd.properties
├── build.gradle
```

## Prerequisites

To run this project, ensure you have the following installed:

- **Java 17**: The project requires Java 17 to run. You can download it from the [official site](https://adoptopenjdk.net/).
- **Gradle**: Gradle is used as the build tool for this project. You can download it from the [official site](https://gradle.org/install/).

Ensure that these tools are properly installed and configured before running the project.

## Build & Run

To build and run the project, use the following commands:

```sh
./gradlew build
```