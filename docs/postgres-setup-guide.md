# PostgreSQL setup guide

This project expects PostgreSQL to be running locally and the Spring Boot app to connect to it using the values in [src/main/resources/application.properties](../src/main/resources/application.properties).

## 1) Install PostgreSQL

On Windows, install PostgreSQL from the official installer or use the PostgreSQL service already installed on your machine.

## 2) Create the database

Open a terminal with PostgreSQL access and run:

```sql
CREATE DATABASE govt_job_zen;
```

## 3) Create the application user

```sql
CREATE USER myapp_user WITH PASSWORD 'myapp_password';
```

## 4) Grant access

```sql
GRANT CONNECT ON DATABASE govt_job_zen TO myapp_user;
GRANT USAGE ON SCHEMA public TO myapp_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO myapp_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO myapp_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO myapp_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO myapp_user;
```

## 5) Connect to the database

```bash
psql -U myapp_user -d govt_job_zen
```

## 6) Apply the migration

The project uses Flyway. If the tables are not present, run the migration script once:

```bash
psql -U myapp_user -d govt_job_zen -f src/main/resources/db/migration/V1__create_base_schema.sql
```

## 7) Verify tables are present

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
```

You should see at least:

- organizations
- states
- categories
- qualifications
- recruitments

## 8) App config

Make sure the application config matches the database:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/govt_job_zen
spring.datasource.username=myapp_user
spring.datasource.password=myapp_password
```

## 9) Common troubleshooting

### Permission denied for table

Run:

```sql
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO myapp_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO myapp_user;
```

### Relation does not exist

This usually means the migration has not been applied. Run the migration script again.

### Duplicate slug error

This usually means the same category or qualification already exists in the database. The app now resolves existing values by name or slug before inserting new records, but you should still keep unique names when seeding reference data.

## 10) Example startup seed data

The migration file already includes initial seed values for:

- SSC, UPSC, IBPS, RRB, MP Police
- All India, Madhya Pradesh, etc.
- Central Government, Banking, Railway, Police, State Government
- Graduate, 12th, Diploma, ITI, B.Tech

This gives new users a usable reference dataset immediately after the schema is created.
