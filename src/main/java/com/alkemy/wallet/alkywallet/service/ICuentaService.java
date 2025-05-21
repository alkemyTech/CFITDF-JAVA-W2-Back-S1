package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaRequestDTO;
import com.alkemy.wallet.alkywallet.dto.ResumenCuentaDTO;

import java.util.List;

public interface ICuentaService {

    // Crear una nueva cuenta
    CuentaDTO crearCuenta(CuentaRequestDTO cuentaDTO);

    // Obtener una cuenta por ID
    CuentaDTO obtenerCuentaPorId(Long id);

    // Listar todas las cuentas activas (no eliminadas)
    List<CuentaDTO> listarCuentas();

    // Actualizar cuenta (por ejemplo, solo tipo o saldo inicial)
    CuentaDTO actualizarCuenta(Long id, CuentaRequestDTO cuentaDTO);

    // Eliminación lógica (soft delete)
    void eliminarCuenta(Long id);

    // 🔍 Extra: listar cuentas por usuario
    List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId);

    // 🔄 Extra: cambiar tipo de cuenta (opcional)
    CuentaDTO cambiarTipoCuenta(Long id, String nuevoTipo);

    ResumenCuentaDTO obtenerResumenCuenta(Long cuentaId);

}
