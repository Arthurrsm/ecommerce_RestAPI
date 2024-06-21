package com.example.Ecommerce_RestAPI.controller;

import com.example.Ecommerce_RestAPI.model.UserEntity;
import com.example.Ecommerce_RestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserEntity usuario) {
        String token = userService.generateToken(usuario.getNome());
        return "Token: " + token;
    }


    @GetMapping("/nomeusuario/{token}")
    public String extractUsername(@PathVariable String token) {
        String username = userService.extractUsername(token);
        return username;
    }

    @Secured("GERENTE")
    @GetMapping(value = "/gerente/{token}")
    public String buscaGerente(@PathVariable String token) {
        System.out.println("Chegou aqui controller");
        String nomeUsuario = userService.extractUsername(token);
        return "Gerente: " + nomeUsuario;
    }

    @Secured("ADMIN")
    @GetMapping(value = "/admin/{token}")
    public String buscaAdmin(@PathVariable String token) {
        System.out.println("Chegou aqui controller");
        String nomeUsuario = userService.extractUsername(token);
        return "Admin: " + nomeUsuario;
    }
}
