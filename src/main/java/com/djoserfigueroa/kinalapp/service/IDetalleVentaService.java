package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.DetalleVenta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    List<DetalleVenta> listarTodos();

    DetalleVenta guardar(DetalleVenta detalleVenta);

    Optional<DetalleVenta> buscarPorCodigo(int codigoDetalleVenta);

    DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta);

    void eliminar(int codigoDetalleVenta);

    @Transactional(readOnly = true)
    boolean existePorCodigo(int codigoDetalleVenta);

    List<DetalleVenta> listarPorVenta(int codigoVenta);

    List<DetalleVenta> listarPorProducto(int codigoProducto);

    void eliminarPorVenta(int codigoVenta);
}