# Sistema de Gestión y Aprobación de Facturas

Proyecto académico para digitalizar la recepción, evaluación y aprobación de facturas.

## Tecnologías
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL 8
- Maven
- JUnit 5

## Funcionalidades implementadas
- Alta y consulta de proveedores.
- Alta y consulta de facturas.
- Consulta por estado y proveedor.
- Aprobación y rechazo de facturas.
- Regla de aprobación: hasta $10.000.000 Gerencia; importes superiores Dirección.
- Integridad referencial Proveedor-Factura.
- Consultas SQL y casos de prueba.

## Ejecución
1. Crear la base ejecutando `sql/01_schema.sql`.
2. Ajustar usuario y contraseña en `src/main/resources/application.properties`.
3. Ejecutar `mvn spring-boot:run`.
4. API disponible en `http://localhost:8080/api`.

## Pruebas
Ejecutar:

```bash
mvn test