# EPP - Sistema de Gestión de Pedidos

Sistema de gestión de pedidos de Equipos de Protección Personal (EPP) desarrollado con arquitectura hexagonal.

## 🚀 Stack Tecnológico

### Backend
- **Spring Boot 3.5.7** (Java 21)
- **PostgreSQL 15**
- **Maven**
- **Arquitectura Hexagonal** (Ports & Adapters)

### Frontend
- **React 18**
- **Vite**
- **Material-UI (MUI)**
- **Axios**

### DevOps
- **Docker** & **Docker Compose**

## 📋 Requisitos

- Java 21
- Node.js 20+
- Maven 3.9+
- Docker & Docker Compose (opcional)
- PostgreSQL 15 (si no usas Docker)

## 🏗️ Estructura del Proyecto

```
epp/
├── epp-back/          # Backend Spring Boot
│   └── src/
│       └── main/java/com/epp/back/pedidos/
│           ├── application/    # Casos de uso y DTOs
│           ├── domain/         # Lógica de negocio
│           └── infrastructure/ # Adaptadores (JPA, REST)
└── epp-front/         # Frontend React
    └── src/
        ├── components/    # Componentes React
        ├── config/        # Configuración
        └── context/       # Context API
```

## 🚀 Ejecución

### Opción 1: Docker Compose (Recomendado)

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# En modo detached
docker-compose up -d --build
```

**Servicios disponibles:**
- Frontend: http://localhost:4173
- Backend: http://localhost:8080
- Base de datos: localhost:5432

### Opción 2: Desarrollo Local

#### Backend

1. Configurar base de datos PostgreSQL:
   ```bash
   # Crear base de datos
   createdb epp_db
   ```

2. Configurar variables de entorno:
   ```bash
   export db_url=jdbc:postgresql://localhost:5432/epp_db
   export db_user=postgres
   export db_password=admin
   export port=8080
   ```

3. Ejecutar aplicación:
   ```bash
   cd epp-back
   ./mvnw spring-boot:run
   ```

#### Frontend

```bash
cd epp-front
npm install
npm run dev
```

Frontend disponible en: http://localhost:5173

## 🔌 API Endpoints

Base URL: `http://localhost:8080/api/v1`

### Pedidos
- `GET /pedidos/list` - Listar pedidos (paginado)
  - Parámetros: `page`, `size`, `sort`
- `GET /pedidos/{id}` - Obtener pedido por ID
- `POST /pedidos/save` - Crear pedido

### Catálogos
- `GET /empresa/list` - Listar empresas
- `GET /area/list` - Listar áreas
- `GET /epp/list` - Listar EPPs
- `GET /producto-quimico/list` - Listar productos químicos

## ⚙️ Configuración

### Variables de Entorno Backend

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `db_url` | URL de conexión a PostgreSQL | - |
| `db_user` | Usuario de base de datos | - |
| `db_password` | Contraseña de base de datos | - |
| `port` | Puerto del servidor | 8080 |

### Variables de Entorno Frontend

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `VITE_API_URL` | URL del backend API | `http://localhost:8080/api/v1` |

## 🐳 Docker

### Construir imágenes individuales

```bash
# Backend
cd epp-back
docker build -t epp-back:latest .

# Frontend
cd epp-front
docker build -t epp-front:latest .
```

### Ejecutar contenedores individuales

```bash
# Backend
docker run -p 8080:8080 \
  -e db_url=jdbc:postgresql://host.docker.internal:5432/epp_db \
  -e db_user=postgres \
  -e db_password=admin \
  -e port=8080 \
  epp-back:latest

# Frontend
docker run -p 4173:4173 \
  -e VITE_API_URL=http://localhost:8080/api/v1 \
  epp-front:latest
```

## 📝 Notas

- La base de datos se crea automáticamente al iniciar el backend (Hibernate DDL auto-update)
- El frontend se conecta al backend mediante la URL configurada en `VITE_API_URL`
- Los datos de PostgreSQL se persisten en el volumen `db_data` cuando se usa Docker Compose



## 📚 Arquitectura

El proyecto sigue **Arquitectura Hexagonal**:

- **Domain**: Lógica de negocio pura (independiente de frameworks)
- **Application**: Casos de uso y orquestación
- **Infrastructure**: Adaptadores (JPA, REST controllers)

Más detalles en [ASSESSMENT.md](./ASSESSMENT.md)

