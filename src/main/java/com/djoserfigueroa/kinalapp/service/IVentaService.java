package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Venta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    /*
     * Interfaz: es un contrato que dice QUÉ métodos debe tener
     * cualquier servicio de Ventas. No tiene
     * implementación, solo la definición de los métodos
     */

    // Método que devuelve una lista de todas las Ventas
    List<Venta> listarTodos();

    // Método que guarda una Venta en base de datos
    // Parámetros: recibe un objeto Venta con los datos a guardar
    Venta guardar(Venta venta);

    // Optional - Contenedor que puede o no tener valor
    // Evita el error de NullPointerException
    Optional<Venta> buscarPorCodigo(int codigoVenta);

    // Método que actualiza una Venta
    // Parámetros - codigoVenta: código de la venta a actualizar
    // Venta venta: objeto con los datos nuevos
    // Retorna un objeto de tipo Venta ya actualizado
    Venta actualizar(int codigoVenta, Venta venta);

    // Método de tipo void para eliminar una Venta
    // void: no retorna ningún valor o dato
    void eliminar(int codigoVenta);

    // boolean - Retorna true si existe y false si no existe
    @Transactional(readOnly = true)
    boolean existePorCodigo(int codigoVenta);

    // Listar ventas activas (estado = 1)
    List<Venta> listarActivas();

    // Listar ventas por cliente usando su DPI
    List<Venta> listarPorCliente(String dpiCliente);

    // Listar ventas por usuario usando su código
    List<Venta> listarPorUsuario(int codigoUsuario);
}