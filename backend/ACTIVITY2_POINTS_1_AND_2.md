# Solucion actividad 2 - puntos 1 y 2

## 1. Contexto del escenario

**Descripcion breve del sistema y objetivo**

LevelUp es una aplicacion de retos de crecimiento personal gamificados orientada a web y movil. Su objetivo es ayudar a los usuarios a mejorar habitos, desarrollar habilidades y mantener la motivacion mediante retos personalizados, seguimiento del progreso, niveles, insignias y una experiencia de acompanamiento dentro de la plataforma.

**Problema que resuelve**

Muchas personas comienzan metas personales como hablar en publico, correr 5 km o fortalecer habilidades academicas, pero abandonan el proceso por falta de constancia, seguimiento y motivacion. LevelUp resuelve este problema al ofrecer una plataforma que organiza los retos y permite medir el avance de manera sencilla.

**Modulos principales del aplicativo**

- Modulo de usuarios: registro, consulta, actualizacion y eliminacion de usuarios.
- Modulo de retos: creacion, consulta, actualizacion y eliminacion de retos personales.
- Modulo de progreso: seguimiento porcentual del avance de cada reto.
- Modulo de gamificacion: niveles, insignias y recompensas virtuales.
- Modulo de comunidad: interaccion, apoyo y motivacion entre usuarios.
- Modulo administrativo: supervision general de la plataforma.

**Actores principales**

- Usuario: crea retos, registra avances y participa en la comunidad.
- Administrador: supervisa el funcionamiento general del sistema.

**Justificacion del escenario**

El escenario es relevante porque responde a una necesidad real: mantener la disciplina y motivacion en procesos de crecimiento personal. Ademas, desde el punto de vista de arquitectura de software, permite trabajar modulos claramente separados, reglas de negocio, seguimiento de datos y futuras integraciones como autenticacion, base de datos y gamificacion.

## 2. CRUD completo

Para cumplir este punto se implemento un CRUD funcional en una API REST con Spring Boot para dos modulos:

- Usuarios
- Retos

La solucion quedo organizada en capas con buenas practicas:

- `controller`: expone los endpoints REST.
- `application/service`: contiene la logica del negocio.
- `application/dto`: define contratos de entrada y salida.
- `application/mapper`: transforma entidades y respuestas.
- `domain/model`: contiene entidades y enums del dominio.
- `repository`: maneja el acceso a datos con JPA.
- `db/migration`: versiona la base de datos con Flyway.

**Operaciones implementadas**

**CRUD de usuarios**

- Create: crear usuarios con nombre, correo y documento.
- Read: consultar un usuario por id y listar todos los usuarios.
- Update: actualizar nombre, correo y datos de documento.
- Delete: eliminar usuarios por id.

**CRUD de retos**

- Create: crear retos asociados a un usuario.
- Read: consultar un reto por id y listar todos los retos.
- Update: actualizar titulo, descripcion, tipo, dificultad y fecha objetivo.
- Delete: eliminar retos por id.

**Valor agregado implementado**

En el modulo de retos tambien se agrego actualizacion de progreso (`updateProgress`), lo que permite reflejar mejor el comportamiento del escenario de negocio. Segun el porcentaje ingresado, el reto cambia automaticamente de estado:

- `CREATED`
- `IN_PROGRESS`
- `COMPLETED`

**Archivos clave de la implementacion**

- `src/main/java/org/example/levelup/infrastructure/controller/UserController.java`
- `src/main/java/org/example/levelup/infrastructure/controller/ChallengeController.java`
- `src/main/java/org/example/levelup/application/service/UserService.java`
- `src/main/java/org/example/levelup/application/service/ChallengeService.java`
- `src/main/java/org/example/levelup/domain/model/UserEntity.java`
- `src/main/java/org/example/levelup/domain/model/ChallengeEntity.java`
- `src/main/resources/db/migration/V1__create_levelup_tables.sql`
- `docker-compose.yml`

**Fragmentos clave para sustentar el punto**

Creacion de usuario:

```java
@PostMapping
public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
    UserResponse response = userService.create(request);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
}
```

Actualizacion de reto:

```java
@PutMapping("/{id}")
public ChallengeResponse update(@PathVariable Long id, @RequestBody @Valid ChallengeRequest request) {
    return challengeService.update(id, request);
}
```

Actualizacion de progreso del reto:

```java
@PatchMapping("/{id}/progress")
public ChallengeResponse updateProgress(@PathVariable Long id, @RequestBody @Valid ChallengeProgressRequest request) {
    return challengeService.updateProgress(id, request);
}
```

Eliminacion de reto:

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    challengeService.delete(id);
    return ResponseEntity.noContent().build();
}
```

**Soporte de validacion**

Tambien se agregaron pruebas de integracion para verificar el CRUD de usuarios y retos:

- `src/test/java/org/example/levelup/infrastructure/controller/UserControllerTest.java`
- `src/test/java/org/example/levelup/infrastructure/controller/ChallengeControllerTest.java`

La base de datos se ejecuta en PostgreSQL con Docker y se inicializa con migraciones Flyway, lo que permite evidenciar una solucion completa desde el controlador hasta la base de datos.
