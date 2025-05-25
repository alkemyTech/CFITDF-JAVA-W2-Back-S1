package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.LoginDTO;
import com.alkemy.wallet.alkywallet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "Auth", description = "Operación para el login")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Iniciar sesión", description = "Autenticación de usuario mediante credenciales")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "Objeto que contiene las credenciales de inicio de sesión")
            @RequestBody LoginDTO loginDTO) {

        if (authService.authenticate(loginDTO)) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}

