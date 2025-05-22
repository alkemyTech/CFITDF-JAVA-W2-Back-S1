package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.UsuarioDTO;
import com.alkemy.wallet.alkywallet.model.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> listarUsuarios();
    Usuario registrarUsuario(Usuario usuario);
    UsuarioDTO actualizarUsuario(Long id, Usuario usuarioActualizado);
    void eliminarUsuario(@NotBlank Long id);
    UsuarioDTO buscarUsuarioPorId(@NotBlank Long id);
    UsuarioDTO buscarUsuarioPorEmail(@NotBlank String email);
    UsuarioDTO convertirADTO(Usuario usuario);
}
