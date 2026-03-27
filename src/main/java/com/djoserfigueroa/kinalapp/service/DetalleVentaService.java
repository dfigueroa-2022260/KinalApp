package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.DetalleVenta;
import com.djoserfigueroa.kinalapp.repository.DetalleVentaRepository;
import com.djoserfigueroa.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository,
                               ProductoRepository productoRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);
        BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                .multiply(new BigDecimal(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);
        detalleVenta.getProducto().setStock(
                detalleVenta.getProducto().getStock() - detalleVenta.getCantidad()
        );
        productoRepository.save(detalleVenta.getProducto());
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigo(int codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("El detalle de venta no se encontró con el código " + codigoDetalleVenta);
        }
        DetalleVenta detalleAnterior = detalleVentaRepository.findById(codigoDetalleVenta).get();
        detalleAnterior.getProducto().setStock(
                detalleAnterior.getProducto().getStock() + detalleAnterior.getCantidad()
        );
        productoRepository.save(detalleAnterior.getProducto());
        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalleVenta(detalleVenta);
        BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                .multiply(new BigDecimal(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);
        detalleVenta.getProducto().setStock(
                detalleVenta.getProducto().getStock() - detalleVenta.getCantidad()
        );
        productoRepository.save(detalleVenta.getProducto());
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(int codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("El detalle de venta no se encontró con el código " + codigoDetalleVenta);
        }
        DetalleVenta detalle = detalleVentaRepository.findById(codigoDetalleVenta).get();
        detalle.getProducto().setStock(
                detalle.getProducto().getStock() + detalle.getCantidad()
        );
        productoRepository.save(detalle.getProducto());
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoDetalleVenta) {
        return detalleVentaRepository.existsById(codigoDetalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorVenta(int codigoVenta) {
        if (codigoVenta <= 0) {
            throw new IllegalArgumentException("El código de venta no es válido");
        }
        return detalleVentaRepository.findByVenta_CodigoVenta(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorProducto(int codigoProducto) {
        if (codigoProducto <= 0) {
            throw new IllegalArgumentException("El código de producto no es válido");
        }
        return detalleVentaRepository.findByProducto_CodigoProducto(codigoProducto);
    }

    @Override
    public void eliminarPorVenta(int codigoVenta) {
        if (!detalleVentaRepository.existsByVenta_CodigoVenta(codigoVenta)) {
            throw new RuntimeException("No se encontraron detalles para la venta con código " + codigoVenta);
        }
        // Revertir stock de todos los productos del detalle
        List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_CodigoVenta(codigoVenta);
        detalles.forEach(detalle -> {
            detalle.getProducto().setStock(
                    detalle.getProducto().getStock() + detalle.getCantidad()
            );
            productoRepository.save(detalle.getProducto());
        });
        detalleVentaRepository.deleteByVenta_CodigoVenta(codigoVenta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            throw new IllegalArgumentException("El detalle de venta no puede ser nulo");
        }
        if (detalleVenta.getVenta() == null) {
            throw new IllegalArgumentException("El detalle debe tener una venta asignada");
        }
        if (detalleVenta.getProducto() == null) {
            throw new IllegalArgumentException("El detalle debe tener un producto asignado");
        }
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (detalleVenta.getProducto().getStock() < detalleVenta.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: "
                    + detalleVenta.getProducto().getNombreProducto());
        }
        if (detalleVenta.getPrecioUnitario() == null || detalleVenta.getPrecioUnitario().doubleValue() < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser nulo o negativo");
        }
    }
}