package com.djoserfigueroa.kinalapp.repository;

import com.djoserfigueroa.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    // Buscar ventas por cliente (FK Clientes_dpi_cliente)
    List<Venta> findByCliente_DPICliente(String dpiCliente);

    // Buscar ventas por usuario (FK Usuarios_codigo_usuario)
    List<Venta> findByUsuario_CodigoUsuario(int codigoUsuario);

    // Buscar ventas activas (estado = 1)
    List<Venta> findByEstado(int estado);
}