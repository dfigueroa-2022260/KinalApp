package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Producto;
import com.djoserfigueroa.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductoViewController {

    private final IProductoService productoService;

    public ProductoViewController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable int codigo, Model model,
                         RedirectAttributes redirectAttributes) {
        return productoService.buscarPorCodigo(codigo)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    return "productos/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
                    return "redirect:/productos";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          RedirectAttributes redirectAttributes) {
        try {
            if (productoService.existePorCodigo(producto.getCodigoProducto())) {
                productoService.actualizar(producto.getCodigoProducto(), producto);
                redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado correctamente");
            } else {
                productoService.guardar(producto);
                redirectAttributes.addFlashAttribute("mensaje", "Producto creado correctamente");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/productos";
    }

    @PostMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable int codigo,
                           RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(codigo);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el producto");
        }
        return "redirect:/productos";
    }
}