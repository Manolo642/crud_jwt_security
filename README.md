# Proyecto CRUD con Seguridad JWT y Spring Boot

## Descripción

Este proyecto implementa un sistema CRUD protegido con autenticación y autorización basada en JWT (JSON Web Tokens) usando Spring Boot y Spring Security.

---

## Flujo y pasos principales para JWT

1. **Dependencias**  
   Se agregaron las librerías necesarias para Spring Security y JWT.

2. **Configuración**  
   Variables definidas en `application.properties`:
   - `app.jwt.secret` (clave secreta para firmar tokens)
   - `app.jwt.expiration` (tiempo de expiración en milisegundos)

3. **JwtUtils**  
   Clase que genera, valida y extrae datos del token JWT, usando la clave secreta y algoritmo HS512.

4. **JwtAuthFilter**  
   Filtro que intercepta las peticiones HTTP para extraer y validar el token JWT del encabezado `Authorization`. Si es válido, autentica al usuario en el contexto de Spring Security.

5. **Configuración de Seguridad (SecurityConfig)**  
   - Registro del filtro JWT.  
   - Definición de rutas públicas (`/auth/login`, `/usuarios/**`) y rutas protegidas (requieren autenticación).  
   - Activación de seguridad a nivel método con anotaciones `@PreAuthorize`.

6. **Endpoint de Login (`/auth/login`)**  
   Valida las credenciales del usuario, y en caso exitoso, genera y devuelve un token JWT.

7. **Protección de Endpoints con Roles**  
   Uso de anotaciones como `@PreAuthorize("hasRole('ADMIN')")` para controlar el acceso según el rol del usuario extraído del token.

---

## Cómo ejecutar

1. Configurar las propiedades en `application.properties` (clave secreta y expiración JWT).  
2. Ejecutar la aplicación Spring Boot.  
3. Usar el endpoint `/auth/login` para obtener un token JWT con las credenciales de un usuario válido.  
4. Acceder a endpoints protegidos usando el token JWT en el header `Authorization` con el prefijo `Bearer `.

---

## Nota

Este proyecto está pensado para un entorno educativo y puede ser ampliado con manejo avanzado de errores, refresco de tokens y mejores prácticas de seguridad.

---

## Autor

Manuel Calderón Matias - 03-Julio-2025

