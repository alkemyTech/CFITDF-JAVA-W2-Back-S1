package com.alkemy.wallet.alkywallet.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.model.Transaccion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaDTO {

	private Long id;
	private String numero;
	private Tarjeta.TipoTarjeta tipo;
	private LocalDate fechaExpiracion;
	private Boolean esVirtual;
	private Boolean deleted;
	private Boolean congelada = false;

	public enum TipoTarjeta {
		CREDITO, DEBITO, PREPAGA,
	}

	private Long cuentaDtoId;
	private UsuarioDTO usuarioDTO;
	private List<Transaccion> transaccionesDTO = new ArrayList<>();

	public TarjetaDTO() {
	}

	public TarjetaDTO(Tarjeta tarjeta) {
		this.id = tarjeta.getId();
		this.numero = tarjeta.getNumero();
		this.tipo = tarjeta.getTipo();
		this.fechaExpiracion = tarjeta.getFechaExpiracion();
		this.esVirtual = tarjeta.getEsVirtual();
		this.cuentaDtoId = tarjeta.getCuenta().getId();
		this.deleted = tarjeta.getDeleted();
		this.congelada = tarjeta.getCongelada();
		if (tarjeta.getUsuario() != null) {
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setNombre(tarjeta.getUsuario().getNombre());
			usuarioDto.setApellido(tarjeta.getUsuario().getApellido());
			this.usuarioDTO = usuarioDto;
		} else {
			this.usuarioDTO = null;
		}
		this.transaccionesDTO = tarjeta.getTransacciones() != null ? tarjeta.getTransacciones() : new ArrayList<>();
	}

}
