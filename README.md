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