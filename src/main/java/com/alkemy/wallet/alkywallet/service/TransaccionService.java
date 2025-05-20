package com.alkemy.wallet.alkywallet.service.impl;

import com.alkemy.wallet.alkywallet.dto.TransaccionRequestDTO;
import com.alkemy.wallet.alkywallet.dto.TransaccionResponseDTO;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.model.Transaccion;
import com.alkemy.wallet.alkywallet.repository.TarjetaRepository;
import com.alkemy.wallet.alkywallet.repository.TransaccionRepository;
import com.alkemy.wallet.alkywallet.service.ITransaccionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaccionService implements ITransaccionService {

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
        return transacciones.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransaccionResponseDTO> obtenerPorId(Long id) {
        return transaccionRepository.findById(id)
                .map(this::mapToResponseDTO);
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
        return new TransaccionResponseDTO(
                transaccion.getId(),
                transaccion.getFecha(),
                transaccion.getMonto(),
                transaccion.getDescripcion(),
                transaccion.getTipoTransaccion(),
                transaccion.getTarjeta().getId()
        );
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
}
