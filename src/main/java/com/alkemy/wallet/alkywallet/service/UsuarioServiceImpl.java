package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.UsuarioDTO;
import com.alkemy.wallet.alkywallet.model.Usuario;
import com.alkemy.wallet.alkywallet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IusuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setBorrado(true);
        usuarioRepository.save(usuario);
    }


    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        // Verifica si el usuario existe y no está borrado
        return usuarioRepository.findByIdAndBorradoFalse(id)
                .map(this::convertirADTO) // Convierte a DTO si se encuentra
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el ID: " + id)); // Lanza excepción si no se encuentra
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        // Verifica si el usuario existe y no está borrado
        return usuarioRepository.findByEmailAndBorradoFalse(email)
                .map(this::convertirADTO) // Convierte a DTO si se encuentra
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el email: " + email)); // Lanza excepción si no se encuentra
    }

    @Override
    public UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}
