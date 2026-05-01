package com.djoserfigueroa.kinalapp.controller;

import com.djoserfigueroa.kinalapp.entity.Usuario;
import com.djoserfigueroa.kinalapp.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ✅ Solo GET — Spring Security maneja el POST automáticamente
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ✅ Solo muestra el formulario
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    // ✅ Registra y redirige al login con ?success
    @PostMapping("/register")
    public String doRegister(@ModelAttribute Usuario usuario,
                             @RequestParam String confirmPassword,
                             Model model) {

        if (!usuario.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            model.addAttribute("usuario", usuario);
            return "register";
        }

        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }

        try {
            usuarioService.guardar(usuario);
            // Redirige al login — el ?success muestra el mensaje verde en login.html
            return "redirect:/login?success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "register";
        }
    }

    // ✅ Página tras login exitoso — Spring Security redirige aquí
    @GetMapping("/dashboard")
    public String dashboard() {
        return "welcome";
    }
}