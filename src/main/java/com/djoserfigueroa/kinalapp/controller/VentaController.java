package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Venta;
import com.djoserfigueroa.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    // GET - Listar todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        List<Venta> ventas = ventaService.listarTodos();
        return ResponseEntity.ok(ventas);
        // 200 OK con la lista de ventas
    }

    // GET - Buscar venta por código
    @GetMapping("/{codigo}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable int codigo) {
        // @PathVariable toma el valor de la URL y lo asigna a codigo
        return ventaService.buscarPorCodigo(codigo)
                // Si Optional tiene valor, devuelve 200 OK con la venta
                .map(ResponseEntity::ok)
                // Si Optional está vacío, devuelve 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Crear una nueva venta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {
        // @RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto Venta
        try {
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
            // 201 CREATED
        } catch (IllegalArgumentException e) {
            // Error de validación → 400 BAD REQUEST con el mensaje de error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE - Eliminar una venta por código
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigo) {
        // ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!ventaService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();
                // 404 Si no existe
            }
            ventaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            // 404 NOT FOUND
        }
    }

    // PUT - Actualizar una venta por código
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable int codigo, @RequestBody Venta venta) {
        try {
            if (!ventaService.existePorCodigo(codigo)) {
                // Verificar si existe antes de actualizar
                return ResponseEntity.notFound().build();
                // 404 Not Found
            }
            Venta ventaActualizada = ventaService.actualizar(codigo, venta);
            return ResponseEntity.ok(ventaActualizada);
            // 200 OK con la venta ya actualizada
        } catch (IllegalArgumentException e) {
            // Error cuando los datos sean incorrectos → 400 BAD REQUEST
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // Cualquier otro error → 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    // GET - Listar ventas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Venta>> listarActivas() {
        List<Venta> activas = ventaService.listarActivas();
        if (activas == null || activas.isEmpty()) {
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT si no hay ventas activas
        }
        return ResponseEntity.ok(activas);
        // 200 OK con la lista
    }

    // GET - Listar ventas por cliente
    @GetMapping("/cliente/{dpi}")
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable String dpi) {
        List<Venta> ventas = ventaService.listarPorCliente(dpi);
        if (ventas == null || ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT si el cliente no tiene ventas
        }
        return ResponseEntity.ok(ventas);
        // 200 OK con las ventas del cliente
    }

    // GET - Listar ventas por usuario
    @GetMapping("/usuario/{codigoUsuario}")
    public ResponseEntity<List<Venta>> listarPorUsuario(@PathVariable int codigoUsuario) {
        List<Venta> ventas = ventaService.listarPorUsuario(codigoUsuario);
        if (ventas == null || ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT si el usuario no tiene ventas
        }
        return ResponseEntity.ok(ventas);
        // 200 OK con las ventas del usuario
    }
}