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

## Google login

Set the following environment variables and register `http://localhost:8080/login/oauth2/code/google`
as an authorized redirect URI in Google Cloud Console.

```bash
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your-client-id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your-client-secret
OAUTH2_SUCCESS_REDIRECT_URI=http://localhost:3000/oauth/callback
```

Start Google login with `GET /oauth2/authorization/google`. The frontend receives JWTs in the callback URL fragment.
