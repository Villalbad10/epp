# ASSESSMENT - Arquitectura y Stack Tecnológico

## 1. ¿Por qué se implementó esta arquitectura?

Se implementó **Arquitectura Hexagonal (Ports and Adapters)** por las siguientes razones:

### Separación de responsabilidades
- **Domain**: Contiene la lógica de negocio pura, independiente de frameworks
- **Application**: Orquesta los casos de uso y transformaciones de datos
- **Infrastructure**: Implementa detalles técnicos (JPA, REST, etc.)

### Independencia de frameworks
- El dominio no depende de Spring, JPA o PostgreSQL
- Permite cambiar la base de datos o framework sin afectar la lógica de negocio
- Facilita testing unitario sin necesidad de bases de datos o contenedores

### Mantenibilidad y escalabilidad
- Cada capa tiene responsabilidades claras
- Facilita la incorporación de nuevos desarrolladores
- Permite evolucionar el sistema sin romper funcionalidades existentes

### Testabilidad
- Los casos de uso se pueden probar con mocks de los ports
- El dominio es testeable sin dependencias externas
- Los adaptadores se prueban de forma aislada

---

## 2. ¿Cómo se haría la comunicación entre módulos?

La comunicación se realiza mediante **Ports (interfaces)** que definen contratos:

### Flujo de comunicación

```
Controller → Service → UseCase (Port In) → Repository Port (Port Out) → Adapter → JPA Repository
```

### Ejemplo práctico:

1. **Controller** (`PedidoController`)
   - Recibe `PedidoRequest` (DTO)
   - Llama al `Service`

2. **Service** (`CrearPedidoService`)
   - Implementa `CrearPedidoUseCase` (Port In)
   - Transforma DTO a modelo de dominio
   - Ejecuta lógica de negocio
   - Usa `PedidoRepositoryPort` (Port Out)

3. **Repository Port** (Interfaz en domain)
   - Define contrato: `Pedido guardar(Pedido pedido)`
   - No conoce implementación

4. **Adapter** (`PedidoRepositoryAdapter`)
   - Implementa `PedidoRepositoryPort`
   - Convierte dominio ↔ entidad JPA
   - Usa `PedidoJpaRepository`

### Ventajas de este enfoque:
- **Desacoplamiento**: El dominio no conoce JPA
- **Intercambiabilidad**: Se puede cambiar JPA por MongoDB sin tocar el dominio
- **Testabilidad**: Se pueden mockear los ports fácilmente

---

## 3. ¿Qué características del Stack Tecnológico aprovecharías?

### Spring Boot 3.5.7

**Características aprovechadas:**
- **Auto-configuración**: Configura automáticamente JPA, Web, Validación
- **Starter dependencies**: Reduce configuración manual
- **Spring Data JPA**: Repositorios con métodos automáticos (`findAll`, `save`, etc.)
- **Spring Validation**: Validación declarativa con anotaciones (`@NotNull`, `@NotEmpty`)
- **Dependency Injection**: Inyección de dependencias con `@RequiredArgsConstructor` (Lombok)
- **Transaction Management**: `@Transactional` para gestión automática de transacciones
- **Exception Handling**: `@ControllerAdvice` para manejo global de excepciones

**Ejemplo de uso:**
```java
@Service
@RequiredArgsConstructor  // Inyección automática
@Transactional            // Gestión automática de transacciones
public class CrearPedidoService implements CrearPedidoUseCase {
    private final PedidoRepositoryPort pedidoRepository;  // Inyectado automáticamente
}
```

### React (Frontend)

**Características a aprovechar:**
- **Componentes reutilizables**: Para formularios, tablas, modales
- **Hooks**: `useState`, `useEffect` para manejo de estado y efectos
- **React Query / SWR**: Para caché y sincronización de datos del backend
- **React Router**: Para navegación entre vistas
- **Context API**: Para estado global (autenticación, tema)
- **Formik + Yup**: Para validación de formularios del lado del cliente

### PostgreSQL

**Características aprovechadas:**
- **ACID**: Transacciones confiables para operaciones críticas
- **Relaciones**: Foreign keys para integridad referencial
- **Índices**: Para optimizar consultas frecuentes
- **Tipos de datos**: `DECIMAL` para precisión en cálculos monetarios
- **JSON/JSONB**: Para almacenar datos flexibles si se requiere
- **Full-text search**: Para búsquedas avanzadas

**Configuración actual:**
```properties
spring.jpa.hibernate.ddl-auto=update  # Actualiza esquema automáticamente
spring.jpa.database=postgresql         # Driver PostgreSQL
```

---

## 4. ¿Qué librerías o servicios adicionales tendrías en cuenta?

### ORM
**Actual:** Spring Data JPA / Hibernate (ya implementado)
- **Ventaja**: Mapeo automático, lazy loading, caché de segundo nivel
- **Recomendación**: Mantener, es estándar y robusto

### Validación
**Actual:** Jakarta Validation (`@NotNull`, `@NotEmpty`)
- **Recomendación adicional**: 
  - **Bean Validation** para validaciones complejas de negocio
  - **Custom validators** para reglas específicas (ej: validar formato NIT)

### Testing
**Actual:** Spring Boot Test (básico)
- **Recomendaciones:**
  - **JUnit 5**: Framework de testing (ya incluido)
  - **Mockito**: Para mocks de dependencias
  - **Testcontainers**: Para tests de integración con PostgreSQL real
  - **AssertJ**: Para aserciones más legibles
  - **MockMvc**: Para tests de endpoints REST

**Ejemplo:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void crearPedido_DeberiaRetornar201() throws Exception {
        // Test con MockMvc
    }
}
```

### Seguridad
- **Spring Security**: Para autenticación y autorización
- **JWT**: Para tokens de sesión
- **OAuth2**: Si se requiere integración con proveedores externos

### Documentación API
- **SpringDoc OpenAPI (Swagger)**: Para documentación automática de endpoints
- **Beneficio**: Documentación interactiva, pruebas desde navegador

### Logging y Monitoreo
- **Logback/SLF4J**: Ya incluido en Spring Boot
- **Recomendación adicional:**
  - **Micrometer**: Para métricas (Prometheus, Grafana)
  - **Sentry**: Para tracking de errores en producción

### Manejo de errores
**Actual:** `GlobalExceptionHandler` básico
- **Recomendación**: 
  - **MapStruct**: Para mapeo automático de excepciones a DTOs de error
  - **Códigos de error estandarizados**: Para mejor comunicación frontend-backend

### Base de datos
- **Flyway o Liquibase**: Para versionado de esquema de base de datos
- **Ventaja**: Migraciones controladas, rollback seguro
- **Recomendación**: Implementar para producción

### Caché
- **Spring Cache**: Para mejorar rendimiento en consultas frecuentes
- **Redis**: Como implementación de caché distribuido (si hay múltiples instancias)

### Mensajería (si se requiere)
- **Spring AMQP / RabbitMQ**: Para comunicación asíncrona entre módulos
- **Kafka**: Para eventos de dominio y arquitectura event-driven

### Build y CI/CD
- **Maven**: Ya implementado
- **Recomendaciones:**
  - **Docker**: Para containerización
  - **GitHub Actions / GitLab CI**: Para pipelines de CI/CD
  - **SonarQube**: Para análisis de calidad de código

---

## Resumen de Stack Recomendado

### Backend (Actual + Recomendado)
- ✅ Spring Boot 3.5.7
- ✅ Spring Data JPA
- ✅ PostgreSQL
- ✅ Lombok
- ✅ Jakarta Validation
- ➕ Spring Security
- ➕ SpringDoc OpenAPI
- ➕ Flyway/Liquibase
- ➕ Testcontainers
- ➕ Micrometer

### Frontend (Recomendado)
- React 18+
- React Query / SWR
- React Router
- Formik + Yup
- Axios / Fetch API
- Material-UI / Ant Design

### DevOps
- Docker
- Docker Compose
- CI/CD Pipeline
- SonarQube

