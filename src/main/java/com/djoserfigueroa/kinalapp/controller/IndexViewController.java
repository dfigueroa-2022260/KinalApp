package com.djoserfigueroa.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexViewController {

    // GET - Página principal
    @GetMapping("/")
    public String index() {
        return "index";
        // Apunta a templates/index.html
    }
}