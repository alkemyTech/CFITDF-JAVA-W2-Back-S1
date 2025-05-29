package com.alkemy.wallet.alkywallet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import com.alkemy.wallet.alkywallet.repository.TarjetaRepository;

@Service
public class TarjetaServiceImpl implements ITarjetaService {
	@Autowired
	private TarjetaRepository tarjetaRepository;

	@Autowired
	private ICuentaRepository cuentaRepository;

	// Crear tarjeta - CREATE
	@Override
	@Transactional
	public TarjetaDTO crearTarjeta(TarjetaDTO dto) {
		if (dto.getCuentaDtoId() == null) {
			throw new IllegalArgumentException("El ID de la cuenta no puede ser nulo");
		}
		Cuenta cuenta = cuentaRepository.findById(dto.getCuentaDtoId()).orElseThrow(() -> {
			return new RuntimeException("No se encontro la cuenta");
		});

		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setUsuario(cuenta.getUsuario());
		tarjeta.setFechaExpiracion(LocalDate.now().plusYears(4));
		tarjeta.setDeleted(false);
		tarjeta.setNumero(generarNumeroTarjetaAleatorio());
		actualizarCamposDesdeDTO(dto, tarjeta);
		tarjetaRepository.save(tarjeta);
		return new TarjetaDTO(tarjeta);
	}

	// Listar todas las tarjetas con el atributo delete = false - READ
	@Override
	public List<TarjetaDTO> listarTarjetas() {
		List<TarjetaDTO> tarjetas = tarjetaRepository.findByDeletedFalse().stream().map(TarjetaDTO::new)
				.collect(Collectors.toList());
		if (tarjetas.isEmpty()) {
			throw new RuntimeException("No se encontraron tarjetas registradas");
		} else
			return tarjetas;
	}

	// Listar tarjeta por id - READ
	@Override
	public TarjetaDTO listarTarjetaPorId(Long id) {
		Tarjeta tarjeta = tarjetaRepository.findByIdAndDeletedFalse(id)
				.orElseThrow(() -> new RuntimeException("No se encontro la tarjeta con el id " + id));
		return new TarjetaDTO(tarjeta);
	}

	@Override
	public List<TarjetaDTO> listarTarjetasPorUsuario(Long id) {
		List<TarjetaDTO> tarjetas = tarjetaRepository.findByUsuarioIdAndDeletedFalse(id).stream().map(TarjetaDTO::new)
				.collect(Collectors.toList());
		if (tarjetas.isEmpty()) {
			throw new RuntimeException("No se encontraron tarjetas registradas para el usuario con el ID " + id);
		} else
			return tarjetas;
	}

	// Editar tarjeta por id - UPDATE
	@Override
	@Transactional
	public TarjetaDTO editarTarjeta(Long id, TarjetaDTO dto) {
		Tarjeta tarjeta = tarjetaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro la tarjeta con el id " + id));
		actualizarCamposDesdeDTO(dto, tarjeta);
		tarjetaRepository.save(tarjeta);
		return new TarjetaDTO(tarjeta);
	}

	// Eliminar tarjeta por id - DELETE
	@Override
	public void eliminarTarjeta(Long id) {
		Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(() -> {
			return new RuntimeException("No se encontro la tarjeta con el id " + id);
		});
		tarjeta.setDeleted(true);
		tarjetaRepository.save(tarjeta);
	}

	private void actualizarCamposDesdeDTO(TarjetaDTO dto, Tarjeta tarjeta) {

		if (dto.getTipo() != null) {
			tarjeta.setTipo(dto.getTipo());
		}

		if (dto.getEsVirtual() != null) {
			tarjeta.setEsVirtual(dto.getEsVirtual());
		}
		if (dto.getCuentaDtoId() != null) {
			Cuenta cuenta = cuentaRepository.findById(dto.getCuentaDtoId())
					.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
			tarjeta.setCuenta(cuenta);
		}
	}

	private String generarNumeroTarjetaAleatorio() {
		StringBuilder numero = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 12; i++) {
			numero.append(random.nextInt(10));
		}
		return numero.toString();
	}

}
