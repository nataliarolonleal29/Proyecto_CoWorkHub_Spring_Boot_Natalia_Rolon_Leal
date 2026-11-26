# CoWorkHub – Sistema de Gestión de Coworking

# Natalia Rolón Leal

## 1. Descripción del proyecto

CoWorkHub es una aplicación web diseñada para gestionar los espacios y servicios de un centro de coworking. El sistema permite administrar sedes, salas, equipamiento, planes de membresía, miembros, servicios adicionales y reservas.

El proyecto está desarrollado mediante una arquitectura cliente-servidor, utilizando Spring Boot para el backend y tecnologías web para el frontend. Además, implementa autenticación mediante JWT y control de acceso basado en roles para proteger las operaciones del sistema.

## 2. Objetivos

- Gestionar las sedes y los espacios disponibles.
- Administrar las salas y sus características.
- Consultar los planes de membresía y servicios adicionales.
- Registrar y consultar miembros.
- Crear, consultar, actualizar y cancelar reservas.
- Calcular los costos asociados a las reservas.
- Controlar el acceso a las funcionalidades según el rol del usuario.
- Proteger los recursos mediante autenticación y autorización.

## 3. Tecnologías utilizadas

### Backend

| Tecnología | Uso |
|---|---|
| Java 25 | Lenguaje de programación |
| Spring Boot 4.1.2 | Framework principal |
| Spring Data JPA | Persistencia de datos |
| Hibernate | Mapeo objeto-relacional |
| Spring Security | Seguridad y autorización |
| JWT | Autenticación mediante tokens |
| MySQL | Base de datos relacional |
| Maven | Gestión de dependencias |
| Swagger / OpenAPI | Documentación y pruebas de API |
| Bean Validation | Validación de datos |

### Frontend

| Tecnología | Uso |
|---|---|
| HTML5 | Estructura de las páginas |
| CSS3 | Diseño y estilos |
| JavaScript | Lógica e interacción |
| Fetch API | Comunicación con el backend |
| SessionStorage | Almacenamiento temporal del token |

## 4. Arquitectura del proyecto

El proyecto utiliza una arquitectura por capas para organizar las responsabilidades del backend.

### Backend

- **Controller:** recibe las solicitudes HTTP y devuelve las respuestas.
- **Service:** contiene la lógica de negocio y las reglas del sistema.
- **Repository:** permite consultar y modificar la información de la base de datos.
- **Model:** contiene las entidades y enumeraciones del sistema.
- **DTO:** define los objetos utilizados para recibir solicitudes y devolver respuestas.
- **Mapper:** transforma las entidades en DTO y viceversa.
- **Security:** gestiona la autenticación, autorización y validación de tokens JWT.
- **Exception:** centraliza el manejo de excepciones.

### Frontend

El frontend está organizado en:

- **HTML:** páginas principales del sistema.
- **CSS:** estilos generales, componentes y adaptación a diferentes pantallas.
- **JavaScript:** comunicación con la API, autenticación, gestión de miembros, catálogos y reservas.

## 5. Funcionalidades principales

### 5.1. Autenticación y seguridad

- Inicio de sesión.
- Autenticación mediante JWT.
- Protección de endpoints.
- Control de acceso según roles.

### 5.2. Gestión de catálogos

Consulta de información relacionada con:

- Sedes.
- Salas.
- Equipamiento.
- Planes de membresía.
- Servicios adicionales.

### 5.3. Gestión de miembros

- Consultar todos los miembros.
- Buscar miembros por identificador.
- Buscar por correo electrónico.
- Buscar por número de documento.
- Registrar nuevos miembros.
- Actualizar información.
- Eliminar miembros cuando se cumplen las reglas de negocio.

Cada miembro tiene un documento, nombre completo, correo electrónico, estado y plan de membresía asociado.

Los estados disponibles son:
- ACTIVE.
- SUSPENDED.

### 5.4. Gestión de reservas

- Registrar reservas.
- Consultar todas las reservas.
- Consultar reservas por identificador.
- Consultar reservas por miembro.
- Actualizar reservas.
- Confirmar reservas.
- Cancelar reservas.
- Completar reservas.

El sistema contempla información como la sala, el miembro, las fechas, los asistentes, los servicios adicionales y los costos asociados.

Los estados disponibles son:

- PENDING.
- CONFIRMED.
- COMPLETED.
- CANCELLED.

### 5.5. Reglas de negocio

El backend implementa validaciones para proteger la integridad de la información, entre ellas:

- Validación de datos obligatorios.
- Validación de identificadores positivos.
- Control de documentos y correos electrónicos duplicados.
- Restricción de eliminación de miembros con reservas asociadas.
- Restricción de eliminación de miembros con cuentas de usuario asociadas.
- Validaciones relacionadas con la disponibilidad y gestión de reservas.
- Cálculo de costos y tarifas de cancelación.

## 6. Roles y permisos

El sistema contempla tres roles principales:

| Rol | Descripción |
|---|---|
| ADMIN | Administración general del sistema y gestión de catálogos. |
| RECEPTIONIST | Gestión de miembros y operaciones de atención y reservas autorizadas. |
| MEMBER | Acceso a las funcionalidades permitidas para los miembros. |

Los permisos se controlan mediante Spring Security y las reglas de autorización configuradas en el backend.

## 7. Requisitos previos

Antes de ejecutar el proyecto se necesita:

- Java JDK 25.
- Apache Maven.
- MySQL Server.
- MySQL Workbench (opcional, para administrar la base de datos).
- Un navegador web.
- Visual Studio Code u otro editor de código.

## 8. Instalación y configuración

### 8.1. Clonar el repositorio

```bash
git clone https://github.com/nataliarolonleal29/Proyecto_CoWorkHub_Spring_Boot_Natalia_Rolon_Leal.git
```

Ingresar a la carpeta del proyecto:

```bash
cd proyecto_spring_boot_Coworking
```

### 8.2. Configurar la base de datos

1. Iniciar MySQL Server.
2. Crear la base de datos correspondiente al proyecto.
3. Abrir el archivo `application.properties`.
4. Configurar la conexión con MySQL.

Ejemplo de configuración:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coworkhub
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Nota:** Reemplazar los valores de ejemplo por las credenciales y el nombre de la base de datos utilizados en el entorno local.

También se debe configurar la clave secreta JWT de acuerdo con la implementación de seguridad del proyecto.

No se deben publicar contraseñas ni claves secretas en el repositorio.

### 8.3. Ejecutar el backend

Desde la carpeta raíz del proyecto ejecutar:

```bash
mvn clean install
```

Posteriormente:

```bash
mvn spring-boot:run
```

El backend estará disponible en:

```text
http://localhost:8080
```

### 8.4. Ejecutar el frontend

1. Abrir la carpeta del frontend en Visual Studio Code.
2. Verificar que el archivo `js/api.js` tenga configurada la dirección correcta del backend:

```javascript
const API_URL = "http://localhost:8080/api";
```

3. Ejecutar el archivo `index.html` utilizando Live Server o un servidor web local.
4. Abrir la dirección proporcionada por el servidor en el navegador.

Es importante mantener el backend ejecutándose mientras se utiliza el frontend.

## 9. Endpoints principales

Los endpoints principales de la API se encuentran organizados de la siguiente manera:

| Recurso | Método HTTP | Endpoint | Funcionalidad |
|---|---|---|---|
| Autenticación | POST | `/api/auth/**` | Operaciones de autenticación |
| Sedes | GET | `/api/locations` | Consultar sedes |
| Salas | GET | `/api/rooms` | Consultar salas |
| Equipamiento | GET | `/api/equipment` | Consultar equipamiento |
| Planes | GET | `/api/membership-plans` | Consultar planes |
| Servicios | GET | `/api/additional-services` | Consultar servicios |
| Miembros | GET | `/api/members` | Consultar miembros |
| Miembros | POST | `/api/members` | Registrar miembros |
| Miembros | PUT | `/api/members/{id}` | Actualizar miembros |
| Miembros | DELETE | `/api/members/{id}` | Eliminar miembros |
| Reservas | GET | `/api/bookings` | Consultar reservas |
| Reservas | POST | `/api/bookings` | Crear reservas |
| Reservas | PUT | `/api/bookings/{id}` | Actualizar reservas |
| Reservas | PATCH | `/api/bookings/{id}/cancel` | Cancelar reservas |
| Reservas | PATCH | `/api/bookings/{id}/confirm` | Confirmar reservas |
| Reservas | PATCH | `/api/bookings/{id}/complete` | Completar reservas |

Para consultar todos los endpoints y sus parámetros se recomienda utilizar Swagger.

## 10. Documentación de la API

El proyecto utiliza Springdoc OpenAPI para generar la documentación de los servicios REST.

Una vez iniciado el backend, acceder a:

**Swagger UI:**

```text
http://localhost:8080/swagger-ui/index.html
```

Desde esta interfaz es posible consultar los endpoints, revisar los modelos de datos y realizar solicitudes HTTP para probar las funcionalidades del backend.

## 11. Comunicación entre frontend y backend

La comunicación se realiza mediante solicitudes HTTP utilizando Fetch API.

El archivo `api.js` centraliza las solicitudes al backend y permite:

- Configurar la URL de la API.
- Recuperar el token JWT.
- Enviar el token en el encabezado Authorization.
- Procesar las respuestas JSON.
- Gestionar los errores HTTP.
- Crear elementos de texto de manera segura en el frontend.

Las operaciones de consulta, registro y actualización se realizan mediante los métodos HTTP correspondientes.

## 12. Seguridad

La seguridad se implementa mediante Spring Security y JSON Web Tokens.

El sistema utiliza:

- Autenticación mediante credenciales.
- Generación y validación de tokens JWT.
- Protección de endpoints privados.
- Autorización basada en roles.
- Sesiones sin estado mediante `STATELESS`.
- Validación de permisos antes de permitir operaciones protegidas.

## 13. Consideraciones finales

CoWorkHub integra un backend desarrollado con Spring Boot y un frontend construido con HTML, CSS y JavaScript.

El sistema permite centralizar la administración de un centro de coworking, facilitando la gestión de sus espacios, miembros, planes, servicios y reservas.

El proyecto aplica conceptos de programación orientada a objetos, arquitectura por capas, persistencia de datos, desarrollo de APIs REST, seguridad informática y comunicación entre aplicaciones web.