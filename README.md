# ieum_server

Spring Boot backend for Ieum ICT.

## Run

```bash
./gradlew bootRun
```

Health check: `GET http://localhost:8080/api/health`
## Google login

Set the following environment variables and register `http://localhost:8080/login/oauth2/code/google`
as an authorized redirect URI in Google Cloud Console.

```bash
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your-client-id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your-client-secret
```

Start Google login with `GET /oauth2/authorization/google`. After Google authentication, the callback returns JWTs as a JSON response.
