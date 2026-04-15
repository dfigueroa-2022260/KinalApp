# KinalApp

Este proyecto representa un sistema de gestión de ventas diseñado para administrar información relacionada con clientes, productos, usuarios del sistema y el proceso completo de una venta.


## Tecnologías Utilizadas

* **Java 17**
* **SpringBoot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de datos)

## Requisitos Previos

Antes de ejecutar el proyecto es importante tener:

* JDK 17 o superior instalado
* MySQL instalado y con una base de datos creada
* Maven configurado en el sistema

## Estructura de la Capa Java
El proyecto sigue una arquitectura organizada por responsabilidades:
* **Entity:** Modelos de datos (`Cliente`, `Producto`, `Venta`, etc.).
* **Repository:** Interfaces para persistencia de datos con JPA.
* **Service:** Lógica de negocio y servicios de usuario personalizados.
* **Controller:** Endpoints de la API para interactuar con el sistema.
* **Config:** Configuraciones globales de seguridad y acceso.

## Cómo se Instala y se ejecuta
Pasos para configurar el entorno y poner en marcha la aplicación:
* **Clonar el repositorio:** Descargar el código fuente desde el repositorio oficial.
* **Configurar Base de Datos:** Crear una base de datos MySQL y actualizar las credenciales en el archivo `KinalApp`.
* **Instalar Dependencias:** En SpringBoot Spring initializr.
* **Ejecutar Aplicación:** Iniciar el proyecto ejecutando en intellij IDEA.

## Estructura del Proyecto
El proyecto está organizado siguiendo una arquitectura por capas:
* **Entity:** Contiene las clases que representan las tablas de la base de datos (`Cliente`, `Producto`, `Venta`, etc.).
* **Repository:** Interfaces encargadas de la comunicación directa con la base de datos mediante Spring Data JPA.
* **Service:** Capa donde se gestiona la lógica de negocio y el servicio personalizado de usuarios.
* **Controller:** Define los puntos de acceso (endpoints) de la API para recibir y responder peticiones HTTP.
* **Config:** Clases de configuración global del sistema, incluyendo la seguridad

El proyecto está organizado siguiendo una arquitectura por capas:

## Estructura de la Entidad VentaLa entidad representa la tabla `ventas` en la base de datos y cuenta con los siguientes campos:
* **id**: Identificador único autoincremental.* **fecha**: Fecha y hora automática de la transacción.* **cliente_id**: Relación con el cliente que realiza la compra.* **total**: Monto final calculado de la operación.* **estado**: Estado de la venta (PENDIENTE, COMPLETADA, CANCELADA).
## Endpoints PrincipalesEl controlador de ventas expone las siguientes rutas:

* `GET /api/ventas`: Obtiene el listado histórico.
* `POST /api/ventas`: Registra una nueva venta.
* `GET /api/ventas/{id}`: Detalle de una venta específica.

## Estructura de la Entidad UsuarioLa entidad representa la tabla `usuarios` en la base de datos y cuenta con los siguientes campos:
* **id**: Identificador único autoincremental.* **username**: Nombre de usuario para el inicio de sesión.* **password**: Contraseña (almacenada con cifrado BCrypt).* **email**: Correo electrónico de contacto y notificaciones.* **rol**: Nivel de permiso (ADMIN, USER, GUEST).
## Endpoints de UsuarioRutas principales para la gestión de usuarios:

* `POST /api/usuarios/registro`: Crea un nuevo usuario en el sistema.
* `GET /api/usuarios/perfil/{id}`: Obtiene la información del perfil.
* `PUT /api/usuarios/actualizar`: Modifica los datos del usuario actual.

markdown# KinalApp

Este proyecto representa un sistema de gestión de ventas diseñado para administrar información relacionada con clientes, productos, usuarios del sistema y el proceso completo de una venta.

---

## Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de Datos)

---

## Requisitos Previos

Antes de ejecutar el proyecto es importante tener:

* JDK 17 o superior instalado
* MySQL instalado y con una base de datos creada
* Maven configurado en el sistema

---

## Estructura de la Capa Java

El proyecto sigue una arquitectura organizada por responsabilidades:

* **Entity:** Modelos de datos (`Cliente`, `Producto`, `Venta`, `Usuario`, `DetalleVenta`).
* **Repository:** Interfaces para persistencia de datos con JPA.
* **Service:** Lógica de negocio y servicios personalizados.
* **Controller:** Endpoints de la API para interactuar con el sistema.
* **Config:** Configuraciones globales de seguridad y acceso.

---

## Cómo se Instala y se Ejecuta

Pasos para configurar el entorno y poner en marcha la aplicación:

* **Clonar el repositorio:** Descargar el código fuente desde el repositorio oficial.
* **Configurar Base de Datos:** Crear una base de datos MySQL y actualizar las credenciales en el archivo `application.properties`.
* **Instalar Dependencias:** Mediante Spring Initializr o ejecutando `mvn install`.
* **Ejecutar Aplicación:** Iniciar el proyecto desde IntelliJ IDEA o con `mvn spring-boot:run`.

---

## Estructura del Proyecto

El proyecto está organizado siguiendo una arquitectura por capas:

* **Entity:** Contiene las clases que representan las tablas de la base de datos.
* **Repository:** Interfaces encargadas de la comunicación directa con la base de datos mediante Spring Data JPA.
* **Service:** Capa donde se gestiona la lógica de negocio.
* **Controller:** Define los puntos de acceso (endpoints) de la API para recibir y responder peticiones HTTP.
* **Config:** Clases de configuración global del sistema, incluyendo la seguridad.

---

## Entidad Cliente

La entidad representa la tabla `clientes` en la base de datos y cuenta con los siguientes campos:

* **dpi_cliente:** Identificador único del cliente (no autoincremental, es el DPI real).
* **nombre_cliente:** Nombre del cliente.
* **apellido_cliente:** Apellido del cliente.
* **direccion:** Dirección de residencia del cliente.
* **estado:** Estado del cliente (1 = activo, 0 = inactivo).

### Endpoints de Cliente

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/clientes` | Lista todos los clientes |
| GET | `/clientes/{dpi}` | Busca un cliente por DPI |
| POST | `/clientes` | Registra un nuevo cliente |
| PUT | `/clientes/{dpi}` | Actualiza los datos de un cliente |
| DELETE | `/clientes/{dpi}` | Elimina un cliente |
| GET | `/clientes/activos` | Lista solo los clientes activos |

---

## Entidad Usuario

La entidad representa la tabla `usuarios` en la base de datos y cuenta con los siguientes campos:

* **codigo_usuario:** Identificador único autoincremental.
* **username:** Nombre de usuario para el inicio de sesión (único).
* **password:** Contraseña del usuario.
* **email:** Correo electrónico de contacto (único).
* **rol:** Nivel de permiso del usuario (ADMIN, USER, etc.).
* **estado:** Estado del usuario (1 = activo, 0 = inactivo).

### Endpoints de Usuario

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/usuarios` | Lista todos los usuarios |
| GET | `/usuarios/{codigo}` | Busca un usuario por código |
| POST | `/usuarios` | Registra un nuevo usuario |
| PUT | `/usuarios/{codigo}` | Actualiza los datos de un usuario |
| DELETE | `/usuarios/{codigo}` | Elimina un usuario |
| GET | `/usuarios/activos` | Lista solo los usuarios activos |
| GET | `/usuarios/username/{username}` | Busca un usuario por username |
| GET | `/usuarios/rol/{rol}` | Lista usuarios por rol |

## Entidad Producto

La entidad representa la tabla `productos` en la base de datos y cuenta con los siguientes campos:

* **codigo_producto:** Identificador único autoincremental.
* **nombre_producto:** Nombre del producto (único).
* **precio:** Precio unitario del producto con dos decimales.
* **stock:** Cantidad disponible en inventario.
* **estado:** Estado del producto (1 = activo, 0 = inactivo).

### Endpoints de Producto

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/productos` | Lista todos los productos |
| GET | `/productos/{codigo}` | Busca un producto por código |
| POST | `/productos` | Registra un nuevo producto |
| PUT | `/productos/{codigo}` | Actualiza los datos de un producto |
| DELETE | `/productos/{codigo}` | Elimina un producto |
| GET | `/productos/activos` | Lista solo los productos activos |
| GET | `/productos/disponibles` | Lista productos con stock mayor a 0 |
| GET | `/productos/buscar/{nombre}` | Busca productos por nombre |
| GET | `/productos/precio/{precio}` | Lista productos hasta un precio máximo |

## Entidad Venta

La entidad representa la tabla `ventas` en la base de datos y cuenta con los siguientes campos:

* **codigo_venta:** Identificador único autoincremental.
* **fecha_venta:** Fecha en que se realizó la venta (se asigna automáticamente si no se envía).
* **total:** Monto total de la venta con dos decimales.
* **estado:** Estado de la venta (1 = activo, 0 = inactivo).
* **Clientes_dpi_cliente:** Relación con el cliente que realiza la compra.
* **Usuarios_codigo_usuario:** Relación con el usuario que registra la venta.

### Endpoints de Venta

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/ventas` | Lista todas las ventas |
| GET | `/ventas/{codigo}` | Busca una venta por código |
| POST | `/ventas` | Registra una nueva venta |
| PUT | `/ventas/{codigo}` | Actualiza los datos de una venta |
| DELETE | `/ventas/{codigo}` | Elimina una venta |
| GET | `/ventas/activas` | Lista solo las ventas activas |
| GET | `/ventas/cliente/{dpi}` | Lista las ventas de un cliente |
| GET | `/ventas/usuario/{codigoUsuario}` | Lista las ventas registradas por un usuario |

---

## Entidad DetalleVenta

La entidad representa la tabla `detalle_venta` en la base de datos y cuenta con los siguientes campos:

* **codigo_detalle_venta:** Identificador único autoincremental.
* **cantidad:** Número de unidades del producto en la venta.
* **precio_unitario:** Precio del producto al momento de la venta.
* **subtotal:** Resultado calculado automáticamente de `cantidad × precio_unitario`.
* **Productos_codigo_producto:** Relación con el producto vendido.
* **Ventas_codigo_venta:** Relación con la venta a la que pertenece el detalle.

### Endpoints de DetalleVenta

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/detalle-ventas` | Lista todos los detalles |
| GET | `/detalle-ventas/{codigo}` | Busca un detalle por código |
| POST | `/detalle-ventas` | Registra un nuevo detalle |
| PUT | `/detalle-ventas/{codigo}` | Actualiza los datos de un detalle |
| DELETE | `/detalle-ventas/{codigo}` | Elimina un detalle |
| GET | `/detalle-ventas/venta/{codigoVenta}` | Lista los detalles de una venta |
| GET | `/detalle-ventas/producto/{codigoProducto}` | Lista los detalles de un producto |
| DELETE | `/detalle-ventas/venta/{codigoVenta}` | Elimina todos los detalles de una venta |

---

## Autor

Desarrollado por **Djoser Figueroa** como proyecto de gestión de ventas con Spring Boot y MySQL