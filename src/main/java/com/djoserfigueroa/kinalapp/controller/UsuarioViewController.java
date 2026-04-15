package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Usuario;
import com.djoserfigueroa.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final IUsuarioService usuarioService;

    public UsuarioViewController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET - Listar todos los usuarios
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
        // Apunta a templates/usuarios/lista.html
    }

    // GET - Mostrar formulario para nuevo usuario
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
        // Apunta a templates/usuarios/formulario.html
    }

    // GET - Mostrar formulario para editar usuario
    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable int codigo, Model model,
                         RedirectAttributes redirectAttributes) {
        return usuarioService.buscarPorCodigo(codigo)
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    return "usuarios/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                    return "redirect:/usuarios";
                });
    }

    // POST - Guardar usuario (crear o actualizar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
                          RedirectAttributes redirectAttributes) {
        try {
            if (usuarioService.existePorCodigo(usuario.getCodigoUsuario())) {
                // Si existe → actualizar
                usuarioService.actualizar(usuario.getCodigoUsuario(), usuario);
                redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
            } else {
                // Si no existe → crear
                usuarioService.guardar(usuario);
                redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    // POST - Eliminar usuario
    @PostMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable int codigo,
                           RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(codigo);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario");
        }
        return "redirect:/usuarios";
    }
}