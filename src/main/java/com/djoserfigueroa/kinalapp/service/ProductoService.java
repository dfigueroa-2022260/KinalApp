package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Producto;
import com.djoserfigueroa.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        if (productoRepository.existsByNombreProducto(producto.getNombreProducto())) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + producto.getNombreProducto());
        }
        if (producto.getEstado() == 0) {
            producto.setEstado(1);
            // Si no se envía estado, se activa por defecto
        }
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigo(int codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede ser nulo o vacío");
        }
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Override
    public Producto actualizar(int codigoProducto, Producto producto) {
        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("El producto no se encontró con el código " + codigoProducto);
        }
        // Verificar que el nombre no lo use OTRO producto
        productoRepository.findAll().stream()
                .filter(p -> p.getNombreProducto().equalsIgnoreCase(producto.getNombreProducto())
                        && p.getCodigoProducto() != codigoProducto)
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Ya existe un producto con el nombre: " + producto.getNombreProducto());
                });
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(int codigoProducto) {
        if (!productoRepository.existsById(codigoProducto)) {
            throw new RuntimeException("El producto no se encontró con el código " + codigoProducto);
        }
        productoRepository.deleteById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarActivos() {
        return productoRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarDisponibles() {
        return productoRepository.findByStockGreaterThan(0);
        // Solo productos con stock mayor a 0
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarPorPrecioMaximo(BigDecimal precioMaximo) {
        if (precioMaximo == null || precioMaximo.doubleValue() < 0) {
            throw new IllegalArgumentException("El precio máximo no puede ser nulo o negativo");
        }
        return productoRepository.findByPrecioLessThanEqual(precioMaximo);
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es un dato obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio().doubleValue() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser nulo o negativo");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock del producto no puede ser negativo");
        }
    }
}