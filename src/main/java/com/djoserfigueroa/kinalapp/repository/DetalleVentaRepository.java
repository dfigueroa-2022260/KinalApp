package com.djoserfigueroa.kinalapp.repository;

import com.djoserfigueroa.kinalapp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    List<DetalleVenta> findByVenta_CodigoVenta(int codigoVenta);

    List<DetalleVenta> findByProducto_CodigoProducto(int codigoProducto);

    boolean existsByVenta_CodigoVenta(int codigoVenta);

    boolean existsByProducto_CodigoProducto(int codigoProducto);

    void deleteByVenta_CodigoVenta(int codigoVenta);
}