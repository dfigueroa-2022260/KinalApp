package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Cliente;
import com.djoserfigueroa.kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RestController = @Controller + @ResponseBody
@RequestMapping("/api/clientes")
// Todas las rutas en este controlador deben empezar por /clientes
public class ClienteController {

    // Inyectamos el SERVICIO y NO el repositorio
    // El controlador solo debe tener conexión con el Servicio
    private final IClienteService clienteService;

    // Como buena práctica la inyección de dependencias debe hacerse por el constructor
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Responde a peticiones GET
    @GetMapping
    // ResponseEntity nos permite controlar el código HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        // Delegamos al servicio
        return ResponseEntity.ok(clientes);
        // 200 OK con la lista de clientes
    }

    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        //@PathVariable Toma el valor de la URL y lo asigna al dpi
        return clienteService.buscarPorDPI(dpi) // Optional<Cliente>
                // Si Optional tiene valor, devuelve 200 OK con el cliente
                .map(ResponseEntity::ok) // Optional<ResponseEntity<>...>
                // Si Optional está vacío, devuelve 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    // POST crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        // @RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        // <?> significa "tipo genérico", puede ser un Cliente o un String
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            // Intentamos guardar el cliente pero puede lanzar una excepción
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            // 201 CREATED (mucho más específico que el 200 para la creación de un cliente)
        } catch (IllegalArgumentException e) {
            // Si hay error de validación
            // 400 BAD REQUEST con el mensaje de error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Delete elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi){
        // ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try{
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
                // 404 Si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT (se ejecutó correctamente y no devuelve cuerpo)

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            // 404 NOT FOUND
        }
    }

    //Actualizar cliente a través del DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if (!clienteService.existePorDPI(dpi)) {
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
                //404 Not Found
            }
            //Actualizamos el cliente pero esto puede lanzar una excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 ok con el cliente ya actualizado
        } catch(IllegalArgumentException e){
            //Error cuando los datos sean incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e){
            //Posiblemente cualquier otro error como: cliente no encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    // GET
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        List<Cliente> activos = clienteService.listarActivos();
        if (activos == null || activos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activos);
    }

}