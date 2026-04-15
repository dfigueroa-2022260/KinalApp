package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Venta;
import com.djoserfigueroa.kinalapp.service.IClienteService;
import com.djoserfigueroa.kinalapp.service.IUsuarioService;
import com.djoserfigueroa.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaViewController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public VentaViewController(IVentaService ventaService,
                               IClienteService clienteService,
                               IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("usuarios", usuarioService.listarActivos());
        return "ventas/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable int codigo, Model model,
                         RedirectAttributes redirectAttributes) {
        return ventaService.buscarPorCodigo(codigo)
                .map(venta -> {
                    model.addAttribute("venta", venta);
                    model.addAttribute("clientes", clienteService.listarActivos());
                    model.addAttribute("usuarios", usuarioService.listarActivos());
                    return "ventas/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Venta no encontrada");
                    return "redirect:/ventas";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta,
                          RedirectAttributes redirectAttributes) {
        try {
            if (ventaService.existePorCodigo(venta.getCodigoVenta())) {
                ventaService.actualizar(venta.getCodigoVenta(), venta);
                redirectAttributes.addFlashAttribute("mensaje", "Venta actualizada correctamente");
            } else {
                ventaService.guardar(venta);
                redirectAttributes.addFlashAttribute("mensaje", "Venta creada correctamente");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/ventas";
    }

    @PostMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable int codigo,
                           RedirectAttributes redirectAttributes) {
        try {
            ventaService.eliminar(codigo);
            redirectAttributes.addFlashAttribute("mensaje", "Venta eliminada correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la venta");
        }
        return "redirect:/ventas";
    }
}