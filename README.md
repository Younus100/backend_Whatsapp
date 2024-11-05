# Real Time Chat Application - Backend

This repository contains the backend code for a **Real Chat Application** that provides real-time messaging features using **Spring Boot**, **Spring Security**, **JWT** (JSON Web Tokens), and **WebSocket** for communication. The application is designed to be secure, scalable, and efficient in handling real-time communication.

## Features

- **User Authentication**: Users can register, login, and authenticate using **JWT** for secure communication.
- **Spring Security**: Integrated for robust security, controlling access to endpoints and handling authorization.
- **REST API**: Provides a set of endpoints for managing users, sending and receiving messages, etc.
- **WebSocket Support**: Enables real-time communication between clients through WebSockets, allowing messages to be sent and received instantly.
- **JWT Authentication**: Protects endpoints and ensures secure data exchange between the client and server.
- **Docker Support**: Dockerized backend for easy deployment in containerized environments.


## Prerequisites

- Java 17 or higher
- Spring Boot 2.x
- A WebSocket client or a web-based chat client (e.g., a frontend application)
- Maven or Gradle for building the project

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/real-chat-backend.git
   cd real-chat-backend

2. **Build the Project: You can either use Maven or Gradle to build the project**:
   ```bash
   mvn clean install

3. **Run the Application:**: \
   **Local Development**: You can run the backend locally using
   ```bash
   mvn spring-boot:run

### Key Sections Explained:

1. **JWT Authentication & Spring Security**: This part explains that the app uses Spring Security for securing the REST API and JWT for authentication.
2. **WebSocket**: The real-time messaging aspect of your chat app is powered by WebSocket, which allows instant message delivery between clients. The WebSocket client example in JavaScript shows how to interact with the backend WebSocket server.
3. **API Endpoints**: A list of REST API endpoints for user registration, login, and chat message management. These require JWT-based authentication.
4. **WebSocket Communication**: How the backend sends messages to specific users through WebSocket and broadcasts them to all connected clients.

