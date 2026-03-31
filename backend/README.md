# LevelUp API

Backend para la aplicacion de retos de crecimiento personal gamificados `LevelUp`.

## Arquitectura

El proyecto sigue una arquitectura en capas:

- `infrastructure/controller`: expone la API REST.
- `application/service`: contiene la logica de negocio.
- `application/dto`: define solicitudes y respuestas.
- `application/mapper`: transforma DTOs y entidades.
- `domain/model`: contiene entidades y enums del dominio.
- `infrastructure/repository`: acceso a datos con Spring Data JPA.
- `db/migration`: migraciones versionadas con Flyway.

## Modulos implementados

- CRUD completo de usuarios.
- CRUD completo de retos.
- Actualizacion de progreso de retos con cambio automatico de estado.
- Validaciones de entrada.
- Manejo centralizado de errores.
- Persistencia en PostgreSQL.
- Contenedorizacion con Docker y Docker Compose.

## Endpoints

### Usuarios

- `POST /api/users`
- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

### Retos

- `POST /api/challenges`
- `GET /api/challenges`
- `GET /api/challenges?userId={userId}`
- `GET /api/challenges/{id}`
- `PUT /api/challenges/{id}`
- `PATCH /api/challenges/{id}/progress`
- `DELETE /api/challenges/{id}`

### Salud

- `GET /api/health`

## Ejecucion local

1. Levantar PostgreSQL con Docker:

```bash
docker compose up -d postgres
```

2. Ejecutar la API:

```bash
./gradlew bootRun
```

En Windows PowerShell:

```powershell
.\gradlew bootRun
```

## Ejecucion completa con Docker

```bash
docker compose up --build
```

La API quedara disponible en `http://localhost:18080`.

## Pruebas

```bash
./gradlew test
```

## Build

```bash
./gradlew bootJar
```
