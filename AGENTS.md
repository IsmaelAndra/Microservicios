# AGENTS.md

Tres servicios Spring Boot independientes, cada uno con su propio Gradle wrapper y `build.gradle`:
- `eurekaserver/` — Servidor Eureka (puerto 8761)
- `courses/` — Cursos CRUD, PostgreSQL `courses_db`, puerto 8080
- `estudiantes/` — Estudiantes CRUD+PATCH, PostgreSQL `estudiantes_db`, puerto 8081

## Prerrequisitos
- Java 17 (toolchain)
- PostgreSQL en `localhost:5432`, dos bases: `courses_db` y `estudiantes_db`, user: `postgres`, pass: `Ismael2003Andrade`

## Orden de inicio
1. `eurekaserver/` primero (puerto 8761)
2. `courses/` y `estudiantes/` después (se registran en Eureka vía dependencia en classpath, sin `@EnableEurekaClient`)

## Arquitectura del proyecto
- Arquitectura en Capas (Controller, Repository, Service, Entity)

## Comandos (ejecutar dentro de cada directorio)
```
./gradlew.bat build
./gradlew.bat bootRun
./gradlew.bat test       # JUnit 5
```

## Stack
Spring Boot 4.0.6, Spring Cloud 2025.1.1, Gradle 9.4.1, JPA + PostgreSQL, Lombok, JUnit 5

## Diferencias entre servicios

| Aspecto | courses | estudiantes |
|---|---|---|
| Puerto | 8080 (implícito) | 8081 (explícito) |
| BD | `courses_db` | `estudiantes_db` |
| Endpoints | `/api/courses` CRUD básico | `/api/estudiantes` CRUD + PATCH + paginación |
| DTOs | No (expone `Course` entity) | Sí (`EstudianteDTO`, `EstudianteRequestDTO`, `EstudiantePatchDTO`) |
| Validación | No | `@Valid` en POST/PUT, `@Email`/`@Min` en PATCH |
| `mainClass` | Implícito | Explícito en `build.gradle:17` |
| Logging | Default | DEBUG nivel paquete, stacktrace siempre |
| Dependencia extra | — | `spring-boot-starter-validation` |

## Config clave
- Eureka: `eureka.client.register-with-eureka=false`, `fetch-registry=false`
- JPA: `ddl-auto=update` en courses y estudiantes
- Courses usa `spring-boot-starter-webmvc` (no `web`)
- `server.error.include-stacktrace=always` y `include-message=always` en estudiantes
