# 🔔 Notification Service

**Notification Service** is a microservice part of the **Food Delivery System**, a distributed architecture designed to manage online food orders.

This service is responsible for listening to system events (via Kafka) and dispatching notifications (Email/SMS) to users. It maintains a record of sent notifications using a PostgreSQL database.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.4.4**
- **PostgreSQL** (Database)
- **Flyway** (Database Migrations)
- **Apache Kafka** (Event Streaming)
- **SpringDoc OpenAPI** (Swagger Documentation)
- **Docker & Docker Compose**

---

## 🚀 Getting Started

### 1. Prerequisites

- Docker & Docker Desktop installed
- Java 21 SDK (if running locally without Docker)
- Maven

---

### 2. Create the Shared Network

Before running the service, create the Docker network that connects all microservices (Notification, Restaurant, Payment, etc.).

```bash
docker network create store-network
```
### 3. Run with Docker Compose (Recommended)

This project includes a `compose.yaml` that orchestrates the Service, Database, Flyway, and PgAdmin.

Navigate to the project root and run:

```bash
docker compose up -d --build
```

## ℹ️ Note

This command will:

- Build the application image
- Start the PostgreSQL database on port **5434**
- Run migration scripts via **Flyway**
- Start **PgAdmin** on port **5052**
- Launch the API on port **8083**

---

## 📦 Services & Access

Once the containers are up, you can access the following interfaces:

| Service        | URL / Access                               | Description                         |
|---------------|---------------------------------------------|-------------------------------------|
| API Endpoints | http://localhost:8083                       | Main REST API                       |
| Swagger UI    | http://localhost:8083/swagger-ui.html       | Interactive API Documentation       |
| PgAdmin       | http://localhost:5052                       | Database management UI              |
| PostgreSQL    | localhost:5434                              | PostgreSQL database                 |

---

## 🔐 Credentials

### PgAdmin

- **User**: admin@admin.com
- **Password**: admin

### PostgreSQL

- **User**: postgres
- **Password**: postgres
- **Database**: notification_db

---

## ⚙️ Configuration

### Environment Variables

The service uses the following environment variables (defined in `compose.yaml`):

| Variable                          | Value                                              | Description                                  |
|----------------------------------|----------------------------------------------------|----------------------------------------------|
| SERVER_PORT                      | 8083                                               | Port where the service runs                  |
| SPRING_DATASOURCE_URL            | jdbc:postgresql://notification_db:5432/...         | Internal Docker connection string            |
| SPRING_KAFKA_BOOTSTRAP_SERVERS   | kafka:9092                                         | Kafka broker address (on store-network)      |

---

## 🗄️ Database Migrations

This project uses **Flyway** to manage database schemas.

Migration files are located at:

```text
src/main/resources/db/migration
```

They are applied automatically when the Flyway container starts.

---

## 👤 Author

**Nelson Almeida**

- 🌐 **Website:** [nelsonalmeida.pt](https://nelsonalmeida.pt)
- 🐙 **GitHub:** [nelsonalmeida2](https://github.com/nelsonalmeida2)

---

> This project is for **educational and portfolio purposes**.
