package com.djoserfigueroa.kinalapp.repository;

import com.djoserfigueroa.kinalapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByEstado(int estado);

    List<Producto> findByNombreProductoContainingIgnoreCase(String nombre);

    List<Producto> findByStockGreaterThan(int stock);

    List<Producto> findByPrecioLessThanEqual(BigDecimal precio);

    boolean existsByNombreProducto(String nombreProducto);
}