package com.alkemy.wallet.alkywallet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alkemy.wallet.alkywallet.dto.TransferenciaRequestDTO;
import com.alkemy.wallet.alkywallet.exception.BadRequestException;
import com.alkemy.wallet.alkywallet.model.*;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.wallet.alkywallet.dto.TransaccionRequestDTO;
import com.alkemy.wallet.alkywallet.dto.TransaccionResponseDTO;
import com.alkemy.wallet.alkywallet.repository.TarjetaRepository;
import com.alkemy.wallet.alkywallet.repository.TransaccionRepository;

@Service
public class TransaccionService implements ITransaccionService, ITransferenciaService {

	@Autowired
	private ICuentaRepository cuentaRepository;

	private final TransaccionRepository transaccionRepository;
	private final TarjetaRepository tarjetaRepository;

	public TransaccionService(TransaccionRepository transaccionRepository, TarjetaRepository tarjetaRepository) {
		this.transaccionRepository = transaccionRepository;
		this.tarjetaRepository = tarjetaRepository;
	}

	@Override
	public TransaccionResponseDTO crearTransaccion(TransaccionRequestDTO requestDTO) {
		Transaccion transaccion = mapToEntity(requestDTO);
		Transaccion guardada = transaccionRepository.save(transaccion);
		return mapToResponseDTO(guardada);
	}

	@Override
	public List<TransaccionResponseDTO> obtenerTodas() {
		List<Transaccion> transacciones = transaccionRepository.findAll();
		return transacciones.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<TransaccionResponseDTO> obtenerPorId(Long id) {
		return transaccionRepository.findById(id).map(this::mapToResponseDTO);
	}

	@Override
	public TransaccionResponseDTO actualizarTransaccion(Long id, TransaccionRequestDTO requestDTO) {
		Transaccion existente = transaccionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

		existente.setFecha(requestDTO.getFecha());
		existente.setMonto(requestDTO.getMonto());
		existente.setDescripcion(requestDTO.getDescripcion());
		existente.setTipoTransaccion(requestDTO.getTipoTransaccion());

		Tarjeta tarjeta = tarjetaRepository.findById(requestDTO.getTarjetaId())
				.orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
		existente.setTarjeta(tarjeta);

		Transaccion actualizada = transaccionRepository.save(existente);
		return mapToResponseDTO(actualizada);
	}

	@Override
	public void eliminarTransaccion(Long id) {
		Transaccion transaccion = transaccionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
		transaccionRepository.delete(transaccion);
	}

	@Override
	public TransaccionResponseDTO mapToResponseDTO(Transaccion transaccion) {
		return new TransaccionResponseDTO(transaccion.getId(), transaccion.getFecha(), transaccion.getMonto(),
				transaccion.getDescripcion(), transaccion.getTipoTransaccion(), transaccion.getTarjeta().getId());
	}

	@Override
	public Transaccion mapToEntity(TransaccionRequestDTO requestDTO) {
		Tarjeta tarjeta = tarjetaRepository.findById(requestDTO.getTarjetaId())
				.orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

		Transaccion transaccion = new Transaccion();
		transaccion.setFecha(requestDTO.getFecha());
		transaccion.setMonto(requestDTO.getMonto());
		transaccion.setDescripcion(requestDTO.getDescripcion());
		transaccion.setTipoTransaccion(requestDTO.getTipoTransaccion());
		transaccion.setTarjeta(tarjeta);

		return transaccion;
	}

	@Transactional
	@Override
	public void realizarTransferencia(TransferenciaRequestDTO dto) {
		Cuenta origen = cuentaRepository.findById(dto.getCuentaOrigenId())
				.orElseThrow(() -> new BadRequestException("Cuenta origen no encontrada"));

		Optional<Cuenta> destinoOpt = cuentaRepository.findByCbu(dto.getCbuDestino());

		if (destinoOpt.isPresent()) {
			Cuenta destino = destinoOpt.get();

			if (origen.getId().equals(destino.getId())) {
				throw new BadRequestException("No se puede transferir a la misma cuenta");
			}

			// ✅ Validación: solo permitir entre cuentas en pesos (Caja de Ahorro o Corriente)
			boolean origenEsPesos = origen.getTipo() == TipoCuenta.CAJA_AHORRO || origen.getTipo() == TipoCuenta.CUENTA_CORRIENTE;
			boolean destinoEsPesos = destino.getTipo() == TipoCuenta.CAJA_AHORRO || destino.getTipo() == TipoCuenta.CUENTA_CORRIENTE;

			if (!(origenEsPesos && destinoEsPesos)) {
				throw new BadRequestException("Solo se permiten transferencias entre cuentas en pesos");
			}

			if (origen.getSaldo() < dto.getMonto()) {
				throw new BadRequestException("Saldo insuficiente en la cuenta origen");
			}

			origen.setSaldo(origen.getSaldo() - dto.getMonto());
			destino.setSaldo(destino.getSaldo() + dto.getMonto());

			Transaccion salida = new Transaccion();
			salida.setFecha(LocalDate.now());
			salida.setMonto(-dto.getMonto());
			salida.setDescripcion(dto.getDescripcion() != null
					? dto.getDescripcion()
					: "Transferencia a CBU " + destino.getCbu());
			salida.setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);

			Transaccion entrada = new Transaccion();
			entrada.setFecha(LocalDate.now());
			entrada.setMonto(dto.getMonto());
			entrada.setDescripcion(dto.getDescripcion() != null
					? dto.getDescripcion()
					: "Transferencia desde cuenta " + origen.getId());
			entrada.setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);

			transaccionRepository.save(salida);
			transaccionRepository.save(entrada);

			origen.getTransacciones().add(salida);
			destino.getTransacciones().add(entrada);

			cuentaRepository.save(origen);
			cuentaRepository.save(destino);

		} else {
			// 🔒 Validar también si origen permite transferencias externas
			boolean origenEsPesos = origen.getTipo() == TipoCuenta.CAJA_AHORRO || origen.getTipo() == TipoCuenta.CUENTA_CORRIENTE;

			if (!origenEsPesos) {
				throw new BadRequestException("No se pueden hacer transferencias externas desde cuentas en moneda extranjera o cripto");
			}

			if (origen.getSaldo() < dto.getMonto()) {
				throw new BadRequestException("Saldo insuficiente en la cuenta origen");
			}

			origen.setSaldo(origen.getSaldo() - dto.getMonto());

			Transaccion salidaExterna = new Transaccion();
			salidaExterna.setFecha(LocalDate.now());
			salidaExterna.setMonto(-dto.getMonto());
			salidaExterna.setDescripcion(dto.getDescripcion() != null
					? dto.getDescripcion()
					: "Transferencia a CBU externo: " + dto.getCbuDestino());
			salidaExterna.setTipoTransaccion(TipoTransaccion.TRANSFERENCIA);

			transaccionRepository.save(salidaExterna);
			origen.getTransacciones().add(salidaExterna);

			cuentaRepository.save(origen);
		}
	}



}
