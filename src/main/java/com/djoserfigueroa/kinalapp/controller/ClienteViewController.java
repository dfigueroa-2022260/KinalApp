package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Cliente;
import com.djoserfigueroa.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteViewController {

    private final IClienteService clienteService;

    public ClienteViewController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET - Listar todos los clientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/lista";
        // Apunta a templates/clientes/lista.html
    }

    // GET - Mostrar formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
        // Apunta a templates/clientes/formulario.html
    }

    // GET - Mostrar formulario para editar cliente
    @GetMapping("/editar/{dpi}")
    public String editar(@PathVariable String dpi, Model model,
                         RedirectAttributes redirectAttributes) {
        return clienteService.buscarPorDPI(dpi)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    return "clientes/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
                    return "redirect:/clientes";
                });
    }

    // POST - Guardar cliente (crear o actualizar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente,
                          RedirectAttributes redirectAttributes) {
        try {
            if (clienteService.existePorDPI(cliente.getDPICliente())) {
                // Si existe → actualizar
                clienteService.actualizar(cliente.getDPICliente(), cliente);
                redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente");
            } else {
                // Si no existe → crear
                clienteService.guardar(cliente);
                redirectAttributes.addFlashAttribute("mensaje", "Cliente creado correctamente");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }

    // POST - Eliminar cliente
    @PostMapping("/eliminar/{dpi}")
    public String eliminar(@PathVariable String dpi,
                           RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminar(dpi);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el cliente");
        }
        return "redirect:/clientes";
    }
}