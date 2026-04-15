package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.DetalleVenta;
import com.djoserfigueroa.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-ventas")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaService.listarTodos();
        return ResponseEntity.ok(detalles);
    }

    // GET - Buscar detalle por código
    @GetMapping("/{codigo}")
    public ResponseEntity<DetalleVenta> buscarPorCodigo(@PathVariable int codigo) {
        // @PathVariable toma el valor de la URL y lo asigna a codigo
        return detalleVentaService.buscarPorCodigo(codigo)
                // Si Optional tiene valor, devuelve 200 OK con el detalle
                .map(ResponseEntity::ok)
                // Si Optional está vacío, devuelve 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta) {
        // @RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto DetalleVenta
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardar(detalleVenta);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE - Eliminar un detalle por código
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigo) {
        // ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!detalleVentaService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();

            }
            detalleVentaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable int codigo, @RequestBody DetalleVenta detalleVenta) {
        try {
            if (!detalleVentaService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();

            }
            DetalleVenta detalleActualizado = detalleVentaService.actualizar(codigo, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
            // 200 OK con el detalle ya actualizado
        } catch (IllegalArgumentException e) {
            // Error cuando los datos sean incorrectos → 400 BAD REQUEST
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Cualquier otro error → 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/venta/{codigoVenta}")
    public ResponseEntity<List<DetalleVenta>> listarPorVenta(@PathVariable int codigoVenta) {
        List<DetalleVenta> detalles = detalleVentaService.listarPorVenta(codigoVenta);
        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    // GET - Listar detalles por producto
    @GetMapping("/producto/{codigoProducto}")
    public ResponseEntity<List<DetalleVenta>> listarPorProducto(@PathVariable int codigoProducto) {
        List<DetalleVenta> detalles = detalleVentaService.listarPorProducto(codigoProducto);
        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(detalles);
    }

    // DELETE - Eliminar todos los detalles de una venta
    @DeleteMapping("/venta/{codigoVenta}")
    public ResponseEntity<Void> eliminarPorVenta(@PathVariable int codigoVenta) {
        try {
            detalleVentaService.eliminarPorVenta(codigoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}