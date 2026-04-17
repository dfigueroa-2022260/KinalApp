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

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {
        try {
            Usuario usuario = usuarioService.buscarPorUsername(username)
                    .orElse(null);

            if (usuario == null || !usuario.getPassword().equals(password)) {
                model.addAttribute("error", "Usuario o contraseña incorrectos.");
                return "login";
            }
            if (usuario.getEstado() != 1) {
                model.addAttribute("error", "Tu cuenta está inactiva.");
                return "login";
            }

            model.addAttribute("usuario", usuario);
            return "welcome";

        } catch (Exception e) {
            model.addAttribute("error", "Error al iniciar sesión.");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

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
            model.addAttribute("success", "Cuenta creada exitosamente. Inicia sesión.");
            return "login";

        } catch (IllegalArgumentException e) {
            // Captura username/email duplicado u otras validaciones del service
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "register";
        }
    }
}