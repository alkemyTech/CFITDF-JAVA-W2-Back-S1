package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.TransaccionRequestDTO;
import com.alkemy.wallet.alkywallet.dto.TransaccionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ITransaccionService {

    TransaccionResponseDTO crearTransaccion(TransaccionRequestDTO requestDTO);

    List<TransaccionResponseDTO> obtenerTodas();

    Optional<TransaccionResponseDTO> obtenerPorId(Long id);

    TransaccionResponseDTO actualizarTransaccion(Long id, TransaccionRequestDTO requestDTO);

    void eliminarTransaccion(Long id);

    TransaccionResponseDTO mapToResponseDTO(com.alkemy.wallet.alkywallet.model.Transaccion transaccion);

    com.alkemy.wallet.alkywallet.model.Transaccion mapToEntity(TransaccionRequestDTO requestDTO);
}
