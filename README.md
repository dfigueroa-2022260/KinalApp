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