package com.alkemy.wallet.alkywallet.service;


import com.alkemy.wallet.alkywallet.dto.TipoCuentaDTO;
import com.alkemy.wallet.alkywallet.model.TipoCuenta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoCuentaServiceImpl implements ItipoCuentaService {

    @Override
    public List<TipoCuentaDTO> obtenerTiposDeCuenta() {
        return List.of(TipoCuenta.values())
                .stream()
                .map(tipo -> new TipoCuentaDTO(tipo.name(), tipo.getDescripcion()))
                .collect(Collectors.toList());

    }
}