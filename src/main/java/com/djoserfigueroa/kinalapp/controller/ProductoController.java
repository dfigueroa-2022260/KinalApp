package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Producto;
import com.djoserfigueroa.kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);

    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> buscarPorCodigo(@PathVariable int codigo) {

        return productoService.buscarPorCodigo(codigo)

                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto) {
        // @RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto Producto
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigo) {

        try {
            if (!productoService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();

            }
            productoService.eliminar(codigo);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();

        }
    }


    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable int codigo, @RequestBody Producto producto) {
        try {
            if (!productoService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();
            }
            Producto productoActualizado = productoService.actualizar(codigo, producto);
            return ResponseEntity.ok(productoActualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarActivos() {
        List<Producto> activos = productoService.listarActivos();
        if (activos == null || activos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> listarDisponibles() {
        List<Producto> disponibles = productoService.listarDisponibles();
        if (disponibles == null || disponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(disponibles);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<Producto> productos = productoService.buscarPorNombre(nombre);
            if (productos == null || productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/precio/{precioMaximo}")
    public ResponseEntity<List<Producto>> listarPorPrecioMaximo(@PathVariable BigDecimal precioMaximo) {
        try {
            List<Producto> productos = productoService.listarPorPrecioMaximo(precioMaximo);
            if (productos == null || productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
