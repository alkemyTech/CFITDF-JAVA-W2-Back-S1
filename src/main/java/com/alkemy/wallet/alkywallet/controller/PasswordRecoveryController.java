package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.CambiarContrasenaRequest;
import com.alkemy.wallet.alkywallet.dto.EmailRequest;
import com.alkemy.wallet.alkywallet.service.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PasswordRecoveryController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<String> recuperarContrasena(@RequestBody EmailRequest emailRequest) {
        try {
            usuarioService.enviarEmailRecuperacion(emailRequest.getEmail());
            return ResponseEntity.ok("Enlace de recuperación enviado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<String> restablecerContrasena(@RequestBody CambiarContrasenaRequest request) {
        try {
            usuarioService.cambiarContrasena(request.getEmail(), request.getNuevaContrasena());
            return ResponseEntity.ok("Contraseña restablecida exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
