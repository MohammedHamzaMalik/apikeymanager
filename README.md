# API Key Manager

A REST API for managing API keys, built with Java 17 and Spring Boot 3.

## What it does
- Create API keys with auto-generated UUID values
- List all keys
- Fetch a single key by ID
- Delete a key
- Input validation with proper error responses
- Global exception handling with clean JSON errors

## Tech stack
- Java 17
- Spring Boot 3.2
- Spring Data JPA / Hibernate
- MySQL 8 (via Docker)
- Maven

## How to run

### Prerequisites
- Java 17+
- Docker

### Start the database
```bash
docker run --name apikeydb -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=apikeymanager -p 3306:3306 -d mysql:8
```

### Run the app
```bash
./mvnw spring-boot:run
```

App runs on `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/keys` | List all API keys |
| GET | `/api/keys/{id}` | Get a key by ID |
| POST | `/api/keys` | Create a new key |
| DELETE | `/api/keys/{id}` | Delete a key |

## Example

**Create a key:**
```bash
curl -X POST http://localhost:8080/api/keys \
  -H "Content-Type: application/json" \
  -d '{"name": "my-service", "owner": "hamza"}'
```

**Response:**
```json
{
  "id": 1,
  "name": "my-service",
  "keyValue": "a3f8c2d1e4b7...",
  "owner": "hamza",
  "createdAt": "2026-03-24T01:05:38"
}
```