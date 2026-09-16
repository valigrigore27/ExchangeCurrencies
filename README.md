# Exchange Currencies

## Run PostgreSQL without pgAdmin

Start Rancher Desktop or Docker Desktop, then run:

```powershell
docker compose up -d
```

The database is available at `localhost:5433` with database `exchange_currencies`,
username `exchange`, and password `exchange`.

Flyway runs automatically when the Spring Boot application starts. It creates
the `exchange` table and inserts the initial exchange rates.

## Run the application

```powershell
./mvnw.cmd spring-boot:run
```

To stop PostgreSQL:

```powershell
docker compose down
```

The database volume is preserved between starts.

## AI usage

AI assistance was used for:

- reviewing the PostgreSQL and Flyway configuration;
- explaining and structuring unit and integration tests;
- explaining MockMvc and Testcontainers usage.

The implementation was reviewed, understood and adapted by the author.
