package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.DetalleVenta;
import com.djoserfigueroa.kinalapp.service.IDetalleVentaService;
import com.djoserfigueroa.kinalapp.service.IProductoService;
import com.djoserfigueroa.kinalapp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/detalle-ventas")
public class DetalleVentaViewController {

    private final IDetalleVentaService detalleVentaService;
    private final IVentaService ventaService;
    private final IProductoService productoService;

    public DetalleVentaViewController(IDetalleVentaService detalleVentaService,
                                      IVentaService ventaService,
                                      IProductoService productoService) {
        this.detalleVentaService = detalleVentaService;
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", detalleVentaService.listarTodos());
        return "detalle-ventas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("ventas", ventaService.listarActivas());
        model.addAttribute("productos", productoService.listarDisponibles());
        return "detalle-ventas/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable int codigo, Model model,
                         RedirectAttributes redirectAttributes) {
        return detalleVentaService.buscarPorCodigo(codigo)
                .map(detalle -> {
                    model.addAttribute("detalle", detalle);
                    model.addAttribute("ventas", ventaService.listarActivas());
                    model.addAttribute("productos", productoService.listarDisponibles());
                    return "detalle-ventas/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Detalle no encontrado");
                    return "redirect:/detalle-ventas";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleVenta detalle,
                          RedirectAttributes redirectAttributes) {
        try {
            if (detalleVentaService.existePorCodigo(detalle.getCodigoDetalleVenta())) {
                detalleVentaService.actualizar(detalle.getCodigoDetalleVenta(), detalle);
                redirectAttributes.addFlashAttribute("mensaje", "Detalle actualizado correctamente");
            } else {
                detalleVentaService.guardar(detalle);
                redirectAttributes.addFlashAttribute("mensaje", "Detalle creado correctamente");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/detalle-ventas";
    }

    @PostMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable int codigo,
                           RedirectAttributes redirectAttributes) {
        try {
            detalleVentaService.eliminar(codigo);
            redirectAttributes.addFlashAttribute("mensaje", "Detalle eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el detalle");
        }
        return "redirect:/detalle-ventas";
    }
}