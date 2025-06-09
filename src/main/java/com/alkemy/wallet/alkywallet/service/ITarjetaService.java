package com.alkemy.wallet.alkywallet.service;

import java.util.List;

import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.model.Tarjeta;

public interface ITarjetaService {

	public List<TarjetaDTO> listarTarjetas();

	public TarjetaDTO listarTarjetaPorId(Long id);

	public List<TarjetaDTO> listarTarjetasPorUsuario(Long id);

	public TarjetaDTO editarTarjeta(Long id, TarjetaDTO dto);

	public void eliminarTarjeta(Long id);

	public TarjetaDTO crearTarjeta(TarjetaDTO dto);

	public TarjetaDTO convertirADTO(Tarjeta tarjeta);

	public void toggleCongelarTarjeta(Long id);
}
