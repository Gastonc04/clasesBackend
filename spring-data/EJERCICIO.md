# Ejercicio: API REST de Tareas

## Objetivos de aprendizaje

- Diseñar una API REST sobre un único recurso aplicando correctamente los verbos HTTP y los códigos de estado.
- Organizar una aplicación Spring Boot en capas inspiradas en **DDD** (*Domain-Driven Design*): **application → domain ← infrastructure**.
- Distinguir entre **servicios de aplicación** (casos de uso que trabajan con DTOs) y **servicios de dominio** (reglas de negocio), y modelar un **dominio rico** (entidades con comportamiento, no solo getters/setters).
- Implementar la capa de persistencia con **Spring Data JPA** sobre una base de datos **H2** en memoria.
- Comprobar que una buena separación en capas permite cambiar la persistencia sin modificar la API.

## Requisitos previos

- Proyecto base provisto (`spring-data`), Spring Boot 4.1.x, Java 21.
- Las dependencias necesarias ya están en el `pom.xml`: `spring-boot-starter-webmvc`, `spring-boot-starter-data-jpa`, `h2`, `spring-boot-h2console` y `lombok`.
- Paquete base: `com.backend.spring_data`.
- Herramienta para probar la API: Postman, Insomnia, HTTPie o `curl`.

---

## Dominio: Tarea

La aplicación gestiona una lista de tareas (un "to-do list"). Cada tarea tiene los siguientes datos:

| Campo              | Tipo                  | Reglas                                                              |
|--------------------|-----------------------|---------------------------------------------------------------------|
| `id`               | `Long`                | Lo genera el sistema. El cliente nunca lo envía.                    |
| `titulo`           | `String`              | Obligatorio, no vacío, máximo 100 caracteres.                       |
| `descripcion`      | `String`              | Opcional, máximo 500 caracteres.                                    |
| `estado`           | `EstadoTarea` (enum)  | `PENDIENTE`, `EN_PROGRESO` o `COMPLETADA`. Por defecto `PENDIENTE`. |
| `fechaVencimiento` | `LocalDate`           | Opcional. Al crear una tarea no puede ser una fecha pasada.         |
| `fechaCreacion`    | `LocalDateTime`       | La asigna el sistema al crear. No se modifica nunca.                |

### Reglas de negocio

1. `titulo` es obligatorio en la creación y en el reemplazo completo (PUT).
2. Una tarea en estado `COMPLETADA` **no puede volver** a `PENDIENTE` (sí puede pasar a `EN_PROGRESO`, por ejemplo si se reabre).
3. `id` y `fechaCreacion` son de solo lectura: si el cliente los envía, se ignoran.
4. Todas estas reglas deben vivir en el **dominio** (métodos de `Tarea` / `EstadoTarea` y `TareaDomainService`). Ni el controller ni el servicio de aplicación deciden reglas de negocio: solo orquestan.

---

# Sección 1 — Diseño de la API, capa de servicios y modelo

En esta sección **no se usa base de datos**. Los datos se guardan en memoria dentro del servicio de aplicación.

## Parte A: Diseño de la API

Todo el ejercicio gira en torno a **un único recurso**: `/api/tareas`. La API debe cumplir con el siguiente contrato:

| Método   | URI                            | Body request        | Éxito                                    | Errores               |
|----------|--------------------------------|---------------------|------------------------------------------|-----------------------|
| `GET`    | `/api/tareas`                  | —                   | `200` + lista (puede estar vacía)        | `400` estado inválido |
| `GET`    | `/api/tareas?estado=PENDIENTE` | —                   | `200` + lista filtrada                   | `400` estado inválido |
| `GET`    | `/api/tareas/{id}`             | —                   | `200` + tarea                            | `404`                 |
| `POST`   | `/api/tareas`                  | `TareaRequest`      | `201` + tarea creada + header `Location` | `400`                 |
| `PUT`    | `/api/tareas/{id}`             | `TareaRequest`      | `200` + tarea actualizada                | `400`, `404`          |
| `PATCH`  | `/api/tareas/{id}`             | `TareaPatchRequest` | `200` + tarea actualizada                | `400`, `404`          |
| `DELETE` | `/api/tareas/{id}`             | —                   | `204` sin cuerpo                         | `404`                 |

- `PUT` reemplaza la tarea completa: todos los campos editables deben enviarse.
- `PATCH` actualiza solo los campos enviados; los que no vienen se mantienen.
- Todos los errores devuelven el mismo formato JSON (ver ejemplo abajo).

### Ejemplos de intercambio

**Crear una tarea**

```http
POST /api/tareas
Content-Type: application/json

{
  "titulo": "Preparar parcial",
  "descripcion": "Repasar Spring Data y JPA",
  "fechaVencimiento": "2026-10-15"
}
```

```http
HTTP/1.1 201 Created
Location: /api/tareas/1
Content-Type: application/json

{
  "id": 1,
  "titulo": "Preparar parcial",
  "descripcion": "Repasar Spring Data y JPA",
  "estado": "PENDIENTE",
  "fechaVencimiento": "2026-10-15",
  "fechaCreacion": "2026-09-29T10:15:30"
}
```

**Actualizar solo el estado (PATCH)**

```http
PATCH /api/tareas/1
Content-Type: application/json

{
  "estado": "EN_PROGRESO"
}
```

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "titulo": "Preparar parcial",
  "descripcion": "Repasar Spring Data y JPA",
  "estado": "EN_PROGRESO",
  "fechaVencimiento": "2026-10-15",
  "fechaCreacion": "2026-09-29T10:15:30"
}
```

**Tarea inexistente (formato de error)**

```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "status": 404,
  "error": "Not Found",
  "mensaje": "No existe la tarea con id 99",
  "timestamp": "2026-09-29T10:20:00"
}
```

## Parte B: Implementación

### Estructura de paquetes (inspirada en DDD)

```
com.backend.spring_data
├── application                          Capa de aplicación: casos de uso y API REST
│   ├── controller
│   │   ├── TareaController              usa solo TareaService
│   │   └── GlobalExceptionHandler       @RestControllerAdvice
│   ├── request
│   │   ├── TareaRequest                 datos para crear / reemplazar
│   │   └── TareaPatchRequest            datos para actualización parcial (todos opcionales)
│   ├── response
│   │   ├── TareaResponse                lo que devuelve la API
│   │   └── ErrorResponse                formato único de error
│   └── service
│       ├── TareaService                 interfaz; sus métodos reciben y devuelven DTOs
│       └── TareaServiceImpl             orquesta: DTO ↔ Tarea, dominio y persistencia
├── domain                               Núcleo del negocio (no conoce DTOs ni HTTP)
│   ├── exception
│   │   ├── TareaNoEncontradaException
│   │   └── ReglaNegocioException
│   ├── model
│   │   ├── Tarea                        entidad rica: crear(...), cambiarEstado(...), actualizar(...)
│   │   └── EstadoTarea                  puedeCambiarA(EstadoTarea)
│   └── service
│       └── TareaDomainService           reglas que no pertenecen a una sola entidad
└── infrastructure                       Detalles técnicos
    ├── config                           opcional (@Configuration, carga de datos, etc.)
    └── repository
        └── TareaRepository              Sección 2: Spring Data JPA
```

| Capa             | Responsabilidad                                                          | Puede usar                              |
|------------------|--------------------------------------------------------------------------|-----------------------------------------|
| `application`    | Exponer la API REST y resolver los casos de uso. Traduce DTOs ↔ dominio. | `domain`, `infrastructure.repository`   |
| `domain`         | Modelo del negocio y sus reglas. Es el corazón de la aplicación.         | Solo a sí mismo (nunca a `application`) |
| `infrastructure` | Detalles técnicos: persistencia, configuración.                          | `domain` (para persistir sus entidades) |

Flujo de una petición: `TareaController → TareaService (application) → Tarea / TareaDomainService (domain) → TareaRepository (infrastructure)`.

### Requisitos

1. **Modelo de dominio** — `domain.model`
   - `EstadoTarea`: enum con `PENDIENTE`, `EN_PROGRESO`, `COMPLETADA` y un método `boolean puedeCambiarA(EstadoTarea nuevo)` que implemente la regla de transición.
   - `Tarea`: **entidad rica**, no un simple contenedor de datos.
     - Se crea mediante un método de fábrica `Tarea.crear(titulo, descripcion, fechaVencimiento)` que asigna estado `PENDIENTE` y `fechaCreacion`, y valida que el título no esté vacío.
     - Expone comportamiento en lugar de setters: `cambiarEstado(EstadoTarea)`, `actualizar(titulo, descripcion, fechaVencimiento, estado)`, etc.
     - `id` y `fechaCreacion` no tienen setters públicos.
     - Si se viola una regla, lanza `ReglaNegocioException`.

2. **Servicio de dominio** — `domain.service`
   - `TareaDomainService` contiene las reglas que no pertenecen naturalmente a la entidad, por ejemplo validar que la `fechaVencimiento` no sea anterior a la fecha actual.
   - Trabaja **solo con objetos del dominio**: no recibe ni devuelve DTOs.

3. **DTOs** — `application.request` y `application.response`
   - `TareaRequest`, `TareaPatchRequest`, `TareaResponse` y `ErrorResponse`.
   - Se sugiere usar `record` de Java.
   - El dominio **no conoce** los DTOs: la conversión `DTO ↔ Tarea` la hace el servicio de aplicación (opcionalmente con una clase `TareaMapper` en `application.service`).

4. **Servicio de aplicación** — `application.service`
   - `TareaService` es una **interfaz** cuyos métodos reciben y devuelven DTOs, por ejemplo:
     - `List<TareaResponse> listar(EstadoTarea estado)`
     - `TareaResponse obtener(Long id)`
     - `TareaResponse crear(TareaRequest request)`
     - `TareaResponse reemplazar(Long id, TareaRequest request)`
     - `TareaResponse actualizarParcial(Long id, TareaPatchRequest request)`
     - `void eliminar(Long id)`
   - `TareaServiceImpl` (`@Service`) **orquesta** cada caso de uso: convierte el request en llamadas al dominio (`Tarea`, `TareaDomainService`), persiste y convierte el resultado en `TareaResponse`.
   - En esta sección guarda las tareas en memoria:
     - un `Map<Long, Tarea>` (por ejemplo `ConcurrentHashMap`) como "tabla",
     - un `AtomicLong` para generar los ids.
   - Si la tarea no existe, lanza `TareaNoEncontradaException`.
   - No implementa reglas de negocio por su cuenta: las delega en el dominio.

5. **Controller** — `application.controller`
   - `@RestController` con `@RequestMapping("/api/tareas")`.
   - Un método por operación usando `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping` y `@DeleteMapping`.
   - Usa `@PathVariable`, `@RequestParam(required = false)` y `@RequestBody`.
   - Devuelve `ResponseEntity` con el código correcto en cada caso (el `POST` debe incluir el header `Location`).
   - **Solo** depende de `TareaService` (la interfaz), inyectada por constructor. Nunca ve la clase `Tarea`.
   - No contiene lógica de negocio.

6. **Manejo de errores**
   - `TareaNoEncontradaException` y `ReglaNegocioException` en `domain.exception` (son conceptos del negocio).
   - `GlobalExceptionHandler` en `application.controller`, anotado con `@RestControllerAdvice` (traducir errores a HTTP es responsabilidad de la capa de aplicación):
     - `TareaNoEncontradaException` → `404`.
     - `ReglaNegocioException` → `400`.
   - Todas las respuestas de error usan `ErrorResponse` con el formato mostrado en la Parte A.

### Pruebas manuales sugeridas

```bash
# Crear
curl -i -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Preparar parcial","fechaVencimiento":"2026-10-15"}'

# Listar todas / filtrar por estado
curl -i http://localhost:8080/api/tareas
curl -i "http://localhost:8080/api/tareas?estado=PENDIENTE"

# Obtener una
curl -i http://localhost:8080/api/tareas/1

# Reemplazar (PUT)
curl -i -X PUT http://localhost:8080/api/tareas/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Preparar final","descripcion":"Todo el programa","estado":"EN_PROGRESO","fechaVencimiento":"2026-12-01"}'

# Actualizar parcialmente (PATCH)
curl -i -X PATCH http://localhost:8080/api/tareas/1 \
  -H "Content-Type: application/json" \
  -d '{"estado":"COMPLETADA"}'

# Regla de negocio: COMPLETADA -> PENDIENTE debe dar 400
curl -i -X PATCH http://localhost:8080/api/tareas/1 \
  -H "Content-Type: application/json" \
  -d '{"estado":"PENDIENTE"}'

# Eliminar
curl -i -X DELETE http://localhost:8080/api/tareas/1

# Inexistente: debe dar 404
curl -i http://localhost:8080/api/tareas/99
```

---

# Sección 2 — Capa de repositorio con Spring Data JPA y H2

En esta sección se reemplaza el `Map` en memoria por una base de datos H2 accedida mediante Spring Data JPA.

> **Restricción clave:** no deben modificarse `application.controller`, `application.request`, `application.response`, la interfaz `TareaService` ni `TareaDomainService`. Si el diseño de la Sección 1 es correcto, solo cambian `Tarea` (anotaciones JPA), `TareaServiceImpl` y la configuración, y se agrega `infrastructure.repository.TareaRepository`.

## Requisitos

1. **Configuración** (`src/main/resources/application.yaml`)
   - Datasource H2 en memoria con URL `jdbc:h2:mem:tareasdb`.
   - `spring.jpa.hibernate.ddl-auto` configurado para que Hibernate cree las tablas automáticamente.
   - `spring.jpa.show-sql: true` para ver las consultas generadas en la consola.
   - Consola H2 habilitada en `/h2-console` (`spring.h2.console.enabled`).

2. **Entidad** — `domain.model`
   - Convertir `Tarea` en una entidad JPA (se anota la misma clase del dominio):
     - `@Entity` (y opcionalmente `@Table(name = "tareas")`).
     - `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` para el `id`.
     - `@Column` reflejando las restricciones del dominio (`nullable`, `length`).
     - `@Enumerated(EnumType.STRING)` para `estado`, así se guarda como texto y no como número.
     - `fechaCreacion` no debe poder actualizarse (`updatable = false`).
   - JPA requiere un constructor sin argumentos: háganlo `protected` para que el resto del código siga usando `Tarea.crear(...)`.
   - Los métodos de comportamiento (`cambiarEstado`, `actualizar`, ...) se mantienen sin cambios.
   - Las anotaciones se importan de `jakarta.persistence.*`.

3. **Repositorio** — `infrastructure.repository`
   - `TareaRepository extends JpaRepository<Tarea, Long>`.
   - Un *query method* derivado para el filtro: `List<Tarea> findByEstado(EstadoTarea estado)`.
   - **No** implementen la interfaz: Spring Data genera la implementación.

4. **Servicio de aplicación** — `application.service`
   - Refactorizar `TareaServiceImpl` para que reciba `TareaRepository` y `TareaDomainService` por constructor.
   - Eliminar el `Map` y el `AtomicLong`: ahora los ids los genera la base de datos.
   - Usar los métodos del repositorio: `findAll`, `findById`, `save`, `existsById`, `deleteById`, `findByEstado`.
   - Marcar con `@Transactional` los casos de uso de escritura (y `@Transactional(readOnly = true)` los de lectura). Las transacciones se definen en el servicio de aplicación, que es quien delimita cada caso de uso.
   - Las reglas de negocio no cambian: siguen en el dominio.

> **Nota sobre DDD:** en un enfoque DDD estricto el repositorio sería una interfaz definida en `domain` (implementada en `infrastructure`) y la entidad JPA sería una clase separada del modelo de dominio. En este ejercicio se simplifica a propósito usando directamente Spring Data y anotando `Tarea`.

5. **Datos iniciales (opcional)**
   - Agregar un `src/main/resources/data.sql` con 3 o 4 tareas de ejemplo y configurar `spring.jpa.defer-datasource-initialization: true` para que se ejecute **después** de que Hibernate cree las tablas.
   - Alternativa: una clase en `infrastructure.config` con un bean `CommandLineRunner` que cree las tareas usando `Tarea.crear(...)` y el repositorio.

## Verificación

1. Levantar la aplicación y repetir **todas** las pruebas de la Sección 1: los resultados deben ser idénticos.
2. Ingresar a `http://localhost:8080/h2-console` con la URL `jdbc:h2:mem:tareasdb` (usuario `sa`, sin contraseña) y ejecutar:
   ```sql
   SELECT * FROM TAREAS;
   ```
   Verificar que los datos creados desde la API aparecen en la tabla y que `estado` se guarda como texto.
3. Observar en la consola de la aplicación el SQL generado por cada operación (`insert`, `select`, `update`, `delete`).

---

## Extras (opcionales)

- **Bean Validation**: agregar `spring-boot-starter-validation` y usar `@NotBlank`, `@Size`, `@FutureOrPresent` en los DTOs con `@Valid` en el controller; manejar `MethodArgumentNotValidException` en el handler.
- **Paginación**: que `GET /api/tareas` acepte `?page=0&size=10&sort=fechaVencimiento` usando `Pageable` en el repositorio y devolviendo `Page<TareaResponse>` desde el servicio de aplicación.
- **Query method adicional**: `GET /api/tareas?vencidas=true` usando `findByFechaVencimientoBeforeAndEstadoNot(LocalDate fecha, EstadoTarea estado)`.
- **Tests**: tests unitarios de `Tarea` y `TareaDomainService` (sin Spring), un test de controller con `@WebMvcTest` (mockeando el servicio) y un test de repositorio con `@DataJpaTest`.
- **DDD estricto**: definir `TareaRepository` como interfaz en `domain.repository`, crear una entidad JPA separada (`TareaEntity`) en `infrastructure.repository` y un adaptador que implemente la interfaz del dominio usando Spring Data.
