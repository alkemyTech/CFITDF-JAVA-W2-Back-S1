package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaRequestDTO;
import com.alkemy.wallet.alkywallet.exception.BadRequestException;
import com.alkemy.wallet.alkywallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.TipoCuenta;
import com.alkemy.wallet.alkywallet.model.Usuario;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import com.alkemy.wallet.alkywallet.repository.UsuarioRepository;
import com.alkemy.wallet.alkywallet.service.ICuentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CuentaServiceImpl implements ICuentaService {

    @Autowired
    private ICuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public CuentaDTO crearCuenta(CuentaRequestDTO dto) {
        log.info("Intentando crear cuenta para usuario ID: {}", dto.getUsuarioId());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", dto.getUsuarioId());
                    return new ResourceNotFoundException("Usuario no encontrado");
                });

        TipoCuenta tipoCuenta = tryParseTipoCuenta(dto.getTipo());
        log.debug("Tipo de cuenta recibido y validado: {}", tipoCuenta);

        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setTipo(tipoCuenta);
        cuenta.setUsuario(usuario);

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        log.info("Cuenta creada exitosamente con ID: {}", cuentaGuardada.getId());

        return new CuentaDTO(cuentaGuardada);
    }

    @Override
    public CuentaDTO obtenerCuentaPorId(Long id) {
        log.info("Buscando cuenta por ID: {}", id);

        return cuentaRepository.findById(id)
                .filter(cuenta -> !cuenta.isDeleted())
                .map(cuenta -> {
                    log.debug("Cuenta encontrada: ID {}", cuenta.getId());
                    return new CuentaDTO(cuenta);
                })
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Cuenta no encontrada");
                });
    }

    @Override
    public List<CuentaDTO> listarCuentas() {
        log.info("Listando todas las cuentas activas");

        List<CuentaDTO> cuentas = cuentaRepository.findByDeletedFalse()
                .stream()
                .map(CuentaDTO::new)
                .collect(Collectors.toList());

        log.debug("Cantidad de cuentas activas encontradas: {}", cuentas.size());
        return cuentas;
    }

    @Override
    public CuentaDTO actualizarCuenta(Long id, CuentaRequestDTO dto) {
        log.info("Actualizando cuenta con ID: {}", id);

        Cuenta cuenta = cuentaRepository.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada para actualización: ID {}", id);
                    return new ResourceNotFoundException("Cuenta no encontrada");
                });

        if (dto.getSaldo() != null && dto.getSaldo() >= 0) {
            log.debug("Actualizando saldo: {}", dto.getSaldo());
            cuenta.setSaldo(dto.getSaldo());
        }

        if (dto.getTipo() != null) {
            TipoCuenta tipoCuenta = tryParseTipoCuenta(dto.getTipo());
            log.debug("Actualizando tipo de cuenta a: {}", tipoCuenta);
            cuenta.setTipo(tipoCuenta);
        }

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        log.info("Cuenta actualizada correctamente: ID {}", cuentaActualizada.getId());

        return new CuentaDTO(cuentaActualizada);
    }

    @Override
    public void eliminarCuenta(Long id) {
        log.info("Eliminando lógicamente cuenta ID: {}", id);

        Cuenta cuenta = cuentaRepository.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada para eliminación: ID {}", id);
                    return new ResourceNotFoundException("Cuenta no encontrada");
                });

        cuenta.setDeleted(true);
        cuentaRepository.save(cuenta);

        log.info("Cuenta marcada como eliminada: ID {}", id);
    }

    @Override
    public List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId) {
        log.info("Listando cuentas del usuario ID: {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado al listar cuentas: ID {}", usuarioId);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });

        List<CuentaDTO> cuentas = cuentaRepository.findByUsuario(usuario)
                .stream()
                .filter(cuenta -> !cuenta.isDeleted())
                .map(CuentaDTO::new)
                .collect(Collectors.toList());

        log.debug("Cantidad de cuentas activas del usuario ID {}: {}", usuarioId, cuentas.size());
        return cuentas;
    }

    @Override
    public CuentaDTO cambiarTipoCuenta(Long id, String nuevoTipo) {
        log.info("Cambiando tipo de cuenta para ID: {} a {}", id, nuevoTipo);

        Cuenta cuenta = cuentaRepository.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada para cambiar tipo: ID {}", id);
                    return new ResourceNotFoundException("Cuenta no encontrada");
                });

        TipoCuenta tipoCuenta = tryParseTipoCuenta(nuevoTipo);
        cuenta.setTipo(tipoCuenta);

        Cuenta actualizada = cuentaRepository.save(cuenta);
        log.info("Tipo de cuenta actualizado para ID {} a {}", id, tipoCuenta);

        return new CuentaDTO(actualizada);
    }

    // ---------- Métodos auxiliares ----------

    private TipoCuenta tryParseTipoCuenta(String tipo) {
        try {
            return TipoCuenta.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Tipo de cuenta inválido recibido: {}", tipo);
            throw new BadRequestException("Tipo de cuenta inválido: " + tipo);
        }
    }
}

