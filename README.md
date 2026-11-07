# Seguros Auto - Backend

Microservicio REST desarrollado con **Spring Boot** para la autenticación de usuarios, consulta de productos de seguros y generación de cotizaciones de vehículos.

## Tecnologías

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- PostgreSQL 16
- Docker & Docker Compose
- Gradle

## Arquitectura

El proyecto está organizado siguiendo una arquitectura por capas:

```
src
└── main
    ├── java
    │   └── hn.demo.seguros
    │       ├── api
    │       ├── application
    │       ├── domain
    │       ├── infrastructure
    │       └── config
    └── resources
        └── application.yml
```

## Requisitos

Antes de ejecutar el proyecto asegúrese de tener instalado:

- Java 17
- Docker Desktop
- Docker Compose
- Git

Verificar versiones:

```bash
java -version
docker --version
docker compose version
```

## Clonar el proyecto

```bash
git clone <URL_DEL_REPOSITORIO>
cd seguros-auto
```

## Configuración

Crear un archivo `.env` en la raíz del proyecto con el siguiente contenido:

```env
DB_PASSWORD=postgres
JWT_SECRET=MiClaveSecretaMuySeguraParaFirmarTokensJWT123456
DEMO_PASSWORD=admin123
FRONTEND_ORIGIN=http://localhost:4200
```

> **Nota:** Cambie los valores por credenciales seguras en un entorno de producción.

## Levantar PostgreSQL

Iniciar la base de datos con Docker Compose:

```bash
docker compose up -d
```

Verificar que el contenedor esté en ejecución:

```bash
docker ps
```

## Ejecutar la aplicación

### En Linux o macOS

```bash
./gradlew bootRun
```

### En Windows

```powershell
.\gradlew.bat bootRun
```

También puede ejecutarse desde IntelliJ IDEA iniciando la clase:

```
SegurosApplication
```

La API estará disponible en:

```
http://localhost:8080
```

## Compilar el proyecto

```bash
./gradlew build
```

En Windows:

```powershell
.\gradlew.bat build
```

## Ejecutar pruebas

```bash
./gradlew test
```

## Base de datos

PostgreSQL se ejecuta mediante Docker.

Configuración por defecto:

| Parámetro | Valor |
|-----------|-------|
| Host | localhost |
| Puerto | 5432 |
| Base de datos | seguros_db |
| Usuario | seguros_user |
| Contraseña | Definida en `DB_PASSWORD` |

## Autenticación

La autenticación utiliza JWT.

Para obtener un token:

**POST**

```
/auth/verify
```

Solicitud:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Respuesta:

```json
{
  "valid": true,
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": 1
}
```

El token debe enviarse en los demás servicios mediante el encabezado:

```
Authorization: Bearer <TOKEN>
```

## Endpoints

### Autenticación

| Método | Endpoint | Descripción |
|---------|----------|-------------|
| POST | `/auth/verify` | Autenticar usuario |

### Productos

| Método | Endpoint | Descripción |
|---------|----------|-------------|
| GET | `/products` | Obtener catálogo de productos |

### Cotizaciones

| Método | Endpoint | Descripción |
|---------|----------|-------------|
| POST | `/quotations` | Generar una cotización |

## Flujo de ejecución

1. Clonar el proyecto.
2. Configurar el archivo `.env`.
3. Iniciar PostgreSQL con Docker Compose.
4. Ejecutar la aplicación Spring Boot.
5. Consumir los servicios REST desde el frontend o Postman.

## Importar la colección de Postman

1. Abrir Postman.
2. Seleccionar **Import**.
3. Elegir el archivo:

```
Seguros-Auto.postman_collection.json
```

4. Ejecutar primero el servicio de autenticación.
5. Copiar el JWT obtenido.
6. Utilizar el token en los servicios protegidos.

## Scripts útiles

Ejecutar aplicación:

```bash
./gradlew bootRun
```

Compilar:

```bash
./gradlew build
```

Limpiar proyecto:

```bash
./gradlew clean
```

Detener contenedores Docker:

```bash
docker compose down
```

Eliminar contenedores y volúmenes:

```bash
docker compose down -v
```

## Autor

Desarrollado como parte de una prueba técnica para un microservicio de cotización de seguros de automóviles utilizando Java, Spring Boot, PostgreSQL, Docker y Angular.