# ExpressLane - Plataforma de Gestión de Envíos

ExpressLane es una plataforma diseñada para facilitar la gestión y el seguimiento de envíos de paquetes. La aplicación permite a los usuarios gestionar sus perfiles, direcciones y paquetes, proporcionando un flujo eficiente y seguro para el envío y recepción de paquetes.

## Características

- **Gestión de Usuarios**: Registro y autenticación de usuarios.
- **Roles y Permisos**: Roles como `ROLE_USER` y `ROLE_ADMIN` para gestionar los accesos y permisos dentro de la plataforma.
- **Gestión de Direcciones**: Permite la creación y gestión de múltiples direcciones (origen y destino).
- **Gestión de Paquetes**: Creación, asignación y seguimiento de envíos de paquetes.
- **Autenticación y Seguridad**: Implementado con Spring Security, incluye manejo de roles y autenticación basada en JWT.
- **Documentación API**: Swagger UI está integrado para documentar y probar los endpoints de la API.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework utilizado para crear la API REST.
- **Spring Security**: Provee autenticación y gestión de roles.
- **JWT**: Para la autenticación de usuarios.
- **Swagger**: Documentación de la API y entorno de pruebas.
- **PostgreSQL**: Base de datos relacional utilizada.
- **JPA/Hibernate**: Mapeo objeto-relacional (ORM) para la interacción con la base de datos.

## Endpoints Principales

- **Usuarios**
  - `POST /api/users`: Crear un nuevo usuario.
  - `GET /api/users`: Obtener todos los usuarios.
  - `GET /api/users/{id}`: Obtener un usuario por su ID.
  - `PUT /api/users/{id}`: Actualizar un usuario.
  - `DELETE /api/users/{id}`: Eliminar un usuario.

- **Paquetes**
  - `POST /api/packages`: Crear un nuevo paquete.
  - `GET /api/packages`: Obtener todos los paquetes.
  - `GET /api/packages/{id}`: Obtener un paquete por su ID.
  - `PUT /api/packages/{id}`: Actualizar un paquete.
  - `DELETE /api/packages/{id}`: Eliminar un paquete.

- **Roles**
  - `GET /api/roles`: Obtener todos los roles disponibles.
  
## Requisitos

- **Java 17** o superior
- **Maven**
- **PostgreSQL** (configuración en `application.properties`)

## Instalación y Configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/expresslane.git

2. Configura la base de datos en el archivo src/main/resources/application.properties:
   ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/expresslane
    spring.datasource.username=usuario
    spring.datasource.password=contraseña
3. Ejecuta el proyecto con Maven:
   ```bash
   mvn spring-boot:run
4. Accede a la documentación de Swagger en:
   ```bash
   http://localhost:8080/swagger-ui.html

## Uso de la API

- Para realizar operaciones protegidas por seguridad (como crear o modificar usuarios/paquetes), deberás autenticarte primero y obtener un token JWT.
- Las operaciones con roles requieren que uses el header Authorization con el token JWT recibido tras iniciar sesión:
  ```bash
  authorization: Bearer <tu-token-jwt>

## Licencia

Este proyecto está bajo la licencia MIT.

Solo necesitas copiar y pegar este contenido en tu archivo **README.md** en el repositorio de GitHub. ¡Está listo para usar!


