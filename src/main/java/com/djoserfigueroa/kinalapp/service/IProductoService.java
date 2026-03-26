package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Producto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorCodigo(int codigoProducto);

    List<Producto> buscarPorNombre(String nombre);

    Producto actualizar(int codigoProducto, Producto producto);

    void eliminar(int codigoProducto);

    @Transactional(readOnly = true)
    boolean existePorCodigo(int codigoProducto);

    List<Producto> listarActivos();

    List<Producto> listarDisponibles();

    List<Producto> listarPorPrecioMaximo(BigDecimal precioMaximo);
}