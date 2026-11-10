# Sistema de Gestion y Aprobacion de Facturas

Proyecto integrador desarrollado en Java para la Actividad Practica 4.

## Descripcion

El Sistema de Gestion y Aprobacion de Facturas es un prototipo informatico disenado para digitalizar el registro, consulta, evaluacion y aprobacion de facturas.

El sistema permite administrar proveedores, registrar facturas, calcular el importe total, consultar comprobantes almacenados y aplicar reglas de aprobacion segun el monto de cada factura.

Los datos se almacenan de forma persistente en una base de datos MySQL.

## Situacion problematica

La recepcion y aprobacion manual de facturas puede provocar:

- Informacion dispersa.
- Errores en los importes.
- Perdida de trazabilidad.
- Demoras en las aprobaciones.
- Autorizaciones realizadas por un nivel incorrecto.
- Dificultades para consultar comprobantes anteriores.

El proyecto centraliza los datos de proveedores y facturas, automatiza las reglas de aprobacion y mantiene la informacion almacenada en MySQL.

## Objetivo

Desarrollar un prototipo que permita:

- Registrar proveedores.
- Registrar facturas.
- Calcular automaticamente el importe total.
- Consultar facturas almacenadas.
- Aprobar o rechazar facturas.
- Asignar el nivel de aprobacion segun el importe.
- Persistir la informacion en MySQL.
- Aplicar Programacion Orientada a Objetos.
- Utilizar arreglos, ArrayList y List.
- Gestionar errores mediante excepciones.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL 8
- Maven
- JUnit 5
- PlantUML
- Visual Studio Code

## Funcionalidades implementadas

- Alta y consulta de proveedores.
- Alta y consulta de facturas.
- Busqueda de facturas por identificador.
- Consulta por estado y proveedor.
- Calculo de importe neto, IVA e importe total.
- Aprobacion y rechazo de facturas.
- Listado de facturas pendientes.
- Ordenamiento de facturas por importe.
- Persistencia de datos en MySQL.
- Manejo de excepciones.
- Menu interactivo por consola.
- Control de niveles de aprobacion.

## Reglas de aprobacion

- Hasta $10.000.000 inclusive: GERENCIA.
- Mas de $10.000.000: DIRECCION.
- Solo pueden evaluarse facturas en estado PENDIENTE.
- Una aprobacion cambia el estado a APROBADA.
- Un rechazo cambia el estado a RECHAZADA.

## Calculo de importes

```text
Importe total = Importe neto + IVA
```

Caso validado:

```text
Importe neto: $10.000.000
IVA:           $2.100.000
Importe total: $12.100.000
Nivel:         DIRECCION
```

## Programacion Orientada a Objetos

### Abstraccion

`Usuario` es una clase abstracta que define los contratos de aprobacion:

```java
public abstract boolean puedeAprobar(Factura factura);
public abstract String getRol();
```

### Encapsulamiento

Los atributos del modelo son privados y se accede a ellos mediante constructores, getters, setters y metodos de negocio. `Factura` encapsula el calculo del importe total.

### Herencia

`Gerente` y `Director` heredan de `Usuario`.

```text
Usuario
|-- Gerente
`-- Director
```

### Polimorfismo

`Gerente` y `Director` redefinen `puedeAprobar(Factura)`. `AprobacionService` trabaja con la abstraccion `Usuario`, por lo que el comportamiento depende del objeto concreto recibido.

## Patron de diseno Strategy

La estrategia variable es la regla de aprobacion.

- Strategy: `Usuario`
- Concrete Strategy: `Gerente`
- Concrete Strategy: `Director`
- Context: `AprobacionService`
- Cliente: `MenuConsola`

El servicio ejecuta:

```java
usuario.puedeAprobar(factura);
```

Este diseno permite incorporar nuevos niveles de aprobacion manteniendo el flujo principal del servicio.

## Arquitectura

El proyecto esta organizado por capas:

### Aplicacion

- `MenuConsola`

### Controladores

- `ProveedorController`
- `FacturaController`

### Servicios

- `ProveedorService`
- `FacturaService`
- `AprobacionService`

### Repositorios

- `ProveedorRepository`
- `FacturaRepository`

### Modelo

- `Proveedor`
- `Factura`
- `Usuario`
- `Gerente`
- `Director`
- `EstadoFactura`

## Estructuras de datos

El proyecto utiliza arreglos, ArrayList y List de forma complementaria.

### Arreglo

El menu utiliza un arreglo fijo:

```java
private static final String[] OPCIONES_MENU = {
        "Registrar proveedor",
        "Registrar factura",
        "Listar facturas",
        "Buscar factura",
        "Aprobar factura",
        "Rechazar factura",
        "Mostrar facturas pendientes",
        "Ordenar facturas por importe",
        "Salir"
};
```

Se recorre mediante:

```java
for (int i = 0; i < OPCIONES_MENU.length; i++) {
    System.out.println((i + 1) + ". " + OPCIONES_MENU[i]);
}
```

### ArrayList

`FacturaService` crea una coleccion modificable antes de ordenar:

```java
ArrayList<Factura> resultado =
        new ArrayList<>(facturas.findAll());

resultado.sort(
        Comparator.comparing(Factura::getImporte)
);
```

### List

Los servicios utilizan `List` como tipo de retorno para colecciones de proveedores y facturas.

## Base de datos

Base utilizada:

```text
gestion_facturas
```

Motor: MySQL 8.

La persistencia utiliza Spring Data JPA, Hibernate y `JpaRepository`.

### Entidad Proveedor

Incluye:

- ID
- CUIT
- Razon social
- Email
- Telefono

### Entidad Factura

Incluye:

- ID
- Numero de factura
- Proveedor
- Fecha de emision
- Fecha de vencimiento
- Importe neto
- IVA
- Importe total
- Estado
- Observaciones

Relacion principal:

```text
Proveedor 1 ----- N Facturas
```

## Estados de factura

```text
PENDIENTE
APROBADA
RECHAZADA
```

Toda factura nueva se registra inicialmente como `PENDIENTE`.

## Manejo de excepciones

El proyecto contempla, entre otras:

- `NumberFormatException`
- `DateTimeParseException`
- `NoSuchElementException`
- `IllegalStateException`
- `IllegalArgumentException`

Estas excepciones controlan entradas numericas y fechas invalidas, registros inexistentes, transiciones de estado incorrectas e intentos de aprobacion sin autorizacion.

## Requisitos previos

- Java 21
- Maven
- MySQL 8
- Base `gestion_facturas`
- Variable de entorno `DB_PASSWORD`

## Creacion de la base

```sql
CREATE DATABASE gestion_facturas;
USE gestion_facturas;
```

## Configuracion MySQL

Archivo:

```text
src/main/resources/application.properties
```

Configuracion:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_facturas
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
aplicacion.menu.habilitado=true
```

La contrasena de MySQL no se almacena en el repositorio.

## Variable de entorno DB_PASSWORD

En PowerShell, para la terminal actual:

```powershell
$env:DB_PASSWORD="CONTRASENA_MYSQL"
```

Para registrarla de manera permanente en Windows:

```powershell
setx DB_PASSWORD "CONTRASENA_MYSQL"
```

## Compilacion

```bash
mvn clean compile
```

Resultado validado:

```text
BUILD SUCCESS
```

## Pruebas automatizadas

```bash
mvn test
```

Resultado validado:

```text
Tests run: 3
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

## Ejecucion

```bash
mvn spring-boot:run
```

Opciones del menu:

```text
1. Registrar proveedor
2. Registrar factura
3. Listar facturas
4. Buscar factura
5. Aprobar factura
6. Rechazar factura
7. Mostrar facturas pendientes
8. Ordenar facturas por importe
9. Salir
```

## Caso funcional validado

Se registro una factura por $12.100.000.

1. La factura quedo PENDIENTE.
2. El nivel requerido fue DIRECCION.
3. GERENCIA intento aprobarla y el sistema rechazo la operacion.
4. DIRECCION realizo la aprobacion correctamente.
5. La factura cambio a APROBADA.
6. El resultado quedo persistido en MySQL.

Este caso demuestra herencia, polimorfismo, Strategy, reglas de negocio, excepciones y persistencia.

## Estructura general

```text
sistema-aprobacion-facturas
|-- docs
|-- sql
|-- src
|   |-- main
|   |   |-- java
|   |   |   `-- ar/com/crivelli/facturas
|   |   |       |-- application
|   |   |       |-- controller
|   |   |       |-- model
|   |   |       |-- repository
|   |   |       `-- service
|   |   `-- resources
|   |       `-- application.properties
|   `-- test
|-- .gitignore
|-- pom.xml
`-- README.md
```

## Documentacion

La carpeta `docs/` contiene los diagramas y documentacion tecnica del proyecto, incluyendo el diagrama UML de clases.

## Video de presentacion

La demostracion de aproximadamente 3 minutos puede incluir:

1. Problema y objetivo.
2. Arquitectura y UML.
3. Patron Strategy y POO.
4. Registro de una factura.
5. Rechazo de Gerencia.
6. Aprobacion de Direccion.
7. Persistencia en MySQL.
8. Tests automatizados.
9. Conclusion.

## Autor

Diego A. Crivelli

Actividad Practica 4  
Proyecto Integrador  
Sistema de Gestion y Aprobacion de Facturas
