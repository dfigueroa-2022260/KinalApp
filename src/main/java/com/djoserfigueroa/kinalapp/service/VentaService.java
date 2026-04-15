package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Venta;
import com.djoserfigueroa.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        if (venta.getEstado() == 0) {
            venta.setEstado(1);
        }
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDate.now());
            // Si no se envía fecha, se asigna la fecha actual
        }
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigo(int codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizar(int codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("La venta no se encontró con el código " + codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(int codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("La venta no se encontró con el código " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(int codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarActivas() {
        return ventaRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorCliente(String dpiCliente) {
        if (dpiCliente == null || dpiCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El DPI del cliente es obligatorio");
        }
        return ventaRepository.findByCliente_DPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorUsuario(int codigoUsuario) {
        if (codigoUsuario <= 0) {
            throw new IllegalArgumentException("El código de usuario no es válido");
        }
        return ventaRepository.findByUsuario_CodigoUsuario(codigoUsuario);
    }

    private void validarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente asignado");
        }
        if (venta.getUsuario() == null) {
            throw new IllegalArgumentException("La venta debe tener un usuario asignado");
        }
        if (venta.getTotal() == null || venta.getTotal().doubleValue() < 0) {
            throw new IllegalArgumentException("El total de la venta no puede ser nulo o negativo");
        }
    }
}