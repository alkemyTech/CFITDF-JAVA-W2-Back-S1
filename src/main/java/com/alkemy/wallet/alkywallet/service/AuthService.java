package com.alkemy.wallet.alkywallet.service;

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

    public boolean authenticate(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndBorradoFalse(loginDTO.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar la contraseña hasheada
            return passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword());
        }
        return false;
    }
}