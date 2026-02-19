# CrimsonCompass Backend 🧭

**CrimsonCompass** is a Tour Management System simplifies trip planning by integrating place searches, bookings, route visualization, and itinerary creation into one platform. It optimizes travel plans, offers suggestions based on ratings, and enables group collaboration for shared itineraries.This robust backend service designed to power a modern travel application. It provides essential APIs for managing user authentication, travel itineraries, place discovery, and social reviews. Built with **Java Spring Boot**, it focuses on scalability, security, and performance.

## 🚀 Key Features

*   **Microservice Architecture**: Designed as a distributed system, integrating with a dedicated **OAuth Microservice** for centralized authentication and authorization, ensuring scalability and separation of concerns.
*   **User Management**: Secure registration, login, and profile management using **JWT (JSON Web Tokens)** and **Spring Security**.
*   **Place Discovery**: Advanced search and filtering for travel destinations (City, Country, Category) with high-performance querying suitable for autocomplete and extensive listings.
*   **Itinerary Planning**: Create, update, and manage personalized travel itineraries.
*   **Social & Reviews**: Users can leave reviews and ratings for places, fostering a community-driven experience.
*   **Media Management**: Handling of place images and associated metadata.
*   **Asynchronous Operations**: Non-blocking email notifications for user engagement.

## 🛠️ Technology Stack

*   **Language**: Java 17
*   **Framework**: Spring Boot 3.x (Web, Data JPA, Security, Mail)
*   **Database**: MySQL (hosted on **Azure Database for MySQL**)
*   **Build Tool**: Maven
*   **Security**: Spring Security, JWT (Json Web Token), BCrypt
*   **Utilities**: Lombok, Jackson

## 👨‍💻 My Role

As the **Backend Software Engineer** for CrimsonCompass, I was responsible for the entire lifecycle of the backend service:

*   **System Architecture**: Designed and implemented the RESTful API architecture using the **Repository-Service-Controller** pattern.
*   **Database Management**: Designed the relational database schema and managed the connection to **Azure Database for MySQL**. Optimized queries using `@EntityGraph` to solve N+1 problems and improve performance.
*   **Security Implementation**: Implemented robust authentication and authorization using JWT and Spring Security, ensuring secure data access.
*   **Deployment**: Orchestrated the entire deployment pipeline, deploying all backend services to **Microsoft Azure**, ensuring high availability and scalability.
*   **Code Quality**: Enforced industry best practices including SOLID principles, dependency injection, and comprehensive unit testing.

## ⚙️ Setup & Installation

### Prerequisites

*   JDK 17 or higher
*   Maven 3.8+
*   MySQL Server (local or remote)

### Configuration

1.  Clone the repository:
    ```bash
    git clone https://github.com/RPreethamR/CrimsonCompass_BE.git
    cd CrimsonCompass_BE
    ```

2.  **Initialize Database**:
    *   Ensure your MySQL server is running.
    *   Import the database schema and dummy data from the `scripts` folder:
        ```bash
        mysql -u your_username -p < scripts/dbSchema_upt.sql
        ```
        *(Or use a tool like MySQL Workbench to open and run `scripts/dbSchema_upt.sql`)*

3.  Configure database connections in `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://your-db-host:3306/crimson_compass_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3.  Configure JWT Security settings:
    ```properties
    jwt.secret=your_super_secret_key_at_least_256_bits
    jwt.expiration=86400000
    ```

### Running the Application

To build and run the application locally:

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## 🧪 Testing

Run unit tests to verify the integrity of the application:

```bash
mvn test
```

## 📚 API Documentation

Key Endpoints include:

*   **Auth**: `POST /api/users/login`, `POST /api/users/register`
*   **Places**: `GET /api/places`, `GET /api/places/search?q={query}`
*   **Itineraries**: `POST /api/itineraries`, `GET /api/itineraries/user/{userId}`
