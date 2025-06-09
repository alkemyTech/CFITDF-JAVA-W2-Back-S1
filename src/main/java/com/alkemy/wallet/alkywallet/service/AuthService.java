package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.AuthResponse;
import com.alkemy.wallet.alkywallet.dto.LoginDTO;
import com.alkemy.wallet.alkywallet.model.Usuario;
import com.alkemy.wallet.alkywallet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para verificar contraseñas hasheadas

    public AuthResponse authenticate(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndBorradoFalse(loginDTO.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar la contraseña hasheada
            if (passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
                // Obtener los IDs de las cuentas
                String[] cuentaIds = usuario.getCuentas().stream()
                        .map(cuenta -> String.valueOf(cuenta.getId())) // Convertir cada ID de cuenta a String
                        .toArray(String[]::new);

                // Construir AuthResponse con todos los atributos necesarios
                return new AuthResponse(
                        "success",
                        "Inicio de sesión exitoso",
                        String.valueOf(usuario.getId()), // Convertir ID a String
                        usuario.getNombre(), // Obtener nombre
                        usuario.getApellido(), // Obtener apellido
                        cuentaIds, // Asignar el arreglo de IDs de cuentas
                        usuario.getRol() // Obtener rol
                );
            } else {
                return new AuthResponse("error", "Credenciales inválidas", null);
            }
        }
        return new AuthResponse("error", "Usuario no encontrado", null);
    }
}