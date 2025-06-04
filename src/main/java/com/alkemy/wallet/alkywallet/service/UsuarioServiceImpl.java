package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.CambiarContraseñaDTO;
import com.alkemy.wallet.alkywallet.dto.UsuarioDTO;
import com.alkemy.wallet.alkywallet.model.Rol;
import com.alkemy.wallet.alkywallet.model.Usuario;
import com.alkemy.wallet.alkywallet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICuentaService cuentaService;

    @Override
    @Transactional // Agregar esta anotación
    public Usuario registrarUsuario(Usuario usuario) {
        // Verifica si ya existe un usuario con el mismo email
        if (usuarioRepository.existsByEmailAndBorradoFalse(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el email: " + usuario.getEmail());
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crear cuenta automáticamente
        try {
            cuentaService.crearCuentaAutomatica(usuarioGuardado);
            log.info("Usuario y cuenta creados exitosamente para: {}", usuario.getEmail());
        } catch (Exception e) {
            log.error("Error al crear cuenta automática para usuario: {} - {}",
                    usuarioGuardado.getId(), e.getMessage());
        }

        return usuarioGuardado;
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, Usuario usuarioActualizado) {
        // Busca el usuario existente
        Usuario usuarioExistente = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        // Actualiza los campos del usuario existente
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setDni(usuarioActualizado.getDni());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
        usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
        usuarioExistente.setNumeroD(usuarioActualizado.getNumeroD());
        usuarioExistente.setProvincia(usuarioActualizado.getProvincia());
        usuarioExistente.setCiudad(usuarioActualizado.getCiudad());

        // Si se proporciona una nueva contraseña, codifícala y actualízala
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        // Guarda el usuario actualizado
        Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);

        // Convierte y devuelve el DTO del usuario actualizado
        return convertirADTO(usuarioGuardado);
    }

    @Override
    @Transactional
    public void cambiarContrasena(Long id, CambiarContraseñaDTO cambiarContraseñaDTO) {
        // Buscar el usuario por ID
        Usuario usuario = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(cambiarContraseñaDTO.getContrasenaActual(), usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }

        // Actualizar la contraseña
        usuario.setPassword(passwordEncoder.encode(cambiarContraseñaDTO.getNuevaContrasena()));
        usuarioRepository.save(usuario);

        log.info("Contraseña actualizada para el usuario con ID: {}", id);
    }



    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setBorrado(true);
        usuarioRepository.save(usuario);
    }

    @Override
    public void desactivarUsuario(@NotBlank Long id){
        Usuario usuario = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void activarUsuario(@NotBlank Long id){
        Usuario usuario = usuarioRepository.findByIdAndBorradoFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    public UsuarioDTO buscarUsuPorId(Long id) {
        // Verifica si el usuario existe y no está borrado
        return usuarioRepository.findByIdAndBorradoFalse(id)
                .map(this::convertirADTO) // Convierte a DTO si se encuentra
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el ID: " + id)); // Lanza excepción si no se encuentra
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        // Obtiene todos los usuarios que no están borrados
        List<Usuario> usuarios = usuarioRepository.findByBorradoFalse();
        return usuarios.stream()
                .filter(usuario -> usuario.getRol() == Rol.CLIENTE)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        // Verifica si el usuario existe y no está borrado, incluyendo cuentas
        return usuarioRepository.findById(id)
                .map(this::convertirADTO) // Convierte a DTO si se encuentra
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el ID: " + id)); // Lanza excepción si no se encuentra
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmailAndBorradoFalse(email)
                .map(this::convertirADTO) // Convierte a DTO si se encuentra
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el email: " + email)); // Lanza excepción si no se encuentra
    }

    @Autowired
    private JavaMailSender mailSender; // Para enviar correos electrónicos

    // Método para enviar el correo de recuperación
    public void enviarEmailRecuperacion(String email) {
        if (!usuarioRepository.existsByEmailAndBorradoFalse(email)) {
            throw new IllegalArgumentException("Correo no registrado");
        }

        String enlaceRecuperacion = "http://localhost:5500/static/restablecerContrase%C3%B1a.html?email=" + email;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Recuperación de Contraseña");
        mensaje.setText("Haz clic en el siguiente enlace para restablecer tu contraseña: " + enlaceRecuperacion);
        mailSender.send(mensaje);

        log.info("Correo de recuperación enviado a: {}", email);
    }

    // Método para cambiar la contraseña
    public void cambiarContrasena(String email, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findByEmailAndBorradoFalse(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el email: " + email));

        // Codificar la nueva contraseña
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);

        log.info("Contraseña actualizada para el usuario: {}", email);
    }

    @Override
    public UsuarioDTO convertirADTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
