package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.UsuarioDTO;
import com.alkemy.wallet.alkywallet.model.Usuario;
import com.alkemy.wallet.alkywallet.service.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo usuario", responses = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            UsuarioDTO dto = usuarioService.convertirADTO(usuarioRegistrado);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> obtenerUsuarioPorId(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No se encontró un usuario con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listado")
    @Operation(summary = "Listar todos los usuarios", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    })
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Obtener usuario por email", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> obtenerUsuarioPorEmail(@Parameter(description = "Email del usuario") @PathVariable String email) {
        try {
            UsuarioDTO usuario = usuarioService.buscarUsuarioPorEmail(email);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No se encontró un usuario con el email: " + email, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> eliminarUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>("Usuario eliminado correctamente.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No se encontró un usuario con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en la actualización")
    })
    public ResponseEntity<?> actualizarUsuario(@Parameter(description = "ID del usuario") @PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            UsuarioDTO usuarioActualizadoDTO = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return new ResponseEntity<>(usuarioActualizadoDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("No se encontró un usuario con el ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el usuario.", HttpStatus.BAD_REQUEST);
        }
    }
}
