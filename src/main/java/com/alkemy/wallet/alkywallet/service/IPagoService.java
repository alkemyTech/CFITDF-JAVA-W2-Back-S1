package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.PagoRequestDTO;
import java.util.List;

public interface IPagoService {
    PagoRequestDTO createPago(PagoRequestDTO pagoRequestDTO);
    List<PagoRequestDTO> getAllPagos();
    PagoRequestDTO getPagoById(Long id);
    PagoRequestDTO updatePago(Long id, PagoRequestDTO pagoRequestDTO);
    void deletePago(Long id);
}
