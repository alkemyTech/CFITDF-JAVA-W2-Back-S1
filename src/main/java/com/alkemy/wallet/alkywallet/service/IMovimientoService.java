package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.MovimientoDTO;

import java.util.List;

public interface IMovimientoService {
    List<MovimientoDTO> obtenerMovimientosPorCuenta(Long cuentaId);
}

