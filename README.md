# govt-job-zen
Backend application for govt job notifications and updates blogs

## Docker + Render deployment

This project is prepared for container deployment on Render using Docker.

### Local Docker run

1. Copy `.env.example` to `.env` and update values for your local or hosted database.
2. Build the image:
   ```bash
   docker build -t govt-job-zen .
   ```
3. Run the container:
   ```bash
   docker run --rm -p 8080:8080 --env-file .env govt-job-zen
   ```

### Render deployment

1. Create a new Render Web Service and choose the repository.
2. Set the runtime to Docker.
3. Keep the default Dockerfile path: `./Dockerfile`.
4. Add environment variables in Render:
   - `SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db_name>?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME=<db_user>`
   - `SPRING_DATASOURCE_PASSWORD=<db_password>`
   - `ADMIN_USERNAME=admin`
   - `ADMIN_PASSWORD=<strong-password>`
5. Render will inject the `PORT` environment variable automatically; the app is configured to listen on that value.

Important: Render's managed Postgres often provides a URL like `postgres://...` or `postgresql://...`. Spring Boot requires a JDBC URL in the form `jdbc:postgresql://...`, so the URL must be converted before it is set in `SPRING_DATASOURCE_URL`.

### Docker Compose

```bash
docker compose up --build
```
This starts the Spring Boot app and a local PostgreSQL database for development.
