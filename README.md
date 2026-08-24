# ieum_server

Spring Boot backend for Ieum ICT.

## Run

```bash
docker compose up -d postgres
./gradlew bootRun
```

Health check: `GET http://localhost:8080/api/health`

## Database

The application uses PostgreSQL for both local and production profiles.

- Local default: `jdbc:postgresql://localhost:5432/ieum`
- Local default credentials: `ieum` / `ieum`
- Override `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` for a different database.
