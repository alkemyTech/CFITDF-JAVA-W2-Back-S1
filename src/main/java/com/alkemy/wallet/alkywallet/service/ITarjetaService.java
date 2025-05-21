package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.Optional;

public interface ITarjetaService {

    public List<TarjetaDTO> listarTarjetas();

    public TarjetaDTO listarTarjetaPorId(Long id);

    public TarjetaDTO editarTarjeta(Long id, TarjetaDTO dto);

    public void eliminarTarjeta(Long id);

    public TarjetaDTO crearTarjeta(TarjetaDTO dto);
}
