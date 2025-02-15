# Messaging Module

The Dundie Awards Messaging Module is a Java-based Spring Boot module responsible for define all the available events and its versions. Also provide the interfaces to be implemented to support publishers for those events.

## Features

- Message handlers for registering different publishers based on their technology, allowing seamless integration with RabbitMQ, Kafka, or any other messaging system by simply implementing the interface.

## Project Structure

The project follows a modular structure with the following main packages:

```
com.ninjaone.dundieawards.activity
│── application
│   ├── messaging
│       ├── publishers
│── domain
│   ├── event
│   ├── enum
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