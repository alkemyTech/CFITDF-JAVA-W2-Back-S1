package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.PagoRequestDTO;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Pago;
import com.alkemy.wallet.alkywallet.repository.IPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepo;

    @Override
    public List<PagoRequestDTO> getAllPagos() {
        return pagoRepo.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PagoRequestDTO getPagoById(Long id) {
        Pago pago = pagoRepo.findById(id).orElse(null);
        return pago != null ? entityToDto(pago) : null;
    }

    @Override
    public PagoRequestDTO createPago(PagoRequestDTO dto) {
        Pago entidad = dtoToEntity(dto);
        Pago guardado = pagoRepo.save(entidad);
        return entityToDto(guardado);
    }

    @Override
    public PagoRequestDTO updatePago(Long id, PagoRequestDTO dto) {
        Pago existente = pagoRepo.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setMonto(dto.getMonto());
        existente.setComercio(dto.getComercio());
        existente.setFecha(Date.from(
                dto.getFecha().atZone(ZoneId.systemDefault()).toInstant()
        ));

        // Actualiza la cuenta referenciada
        Cuenta cuenta = new Cuenta();
        cuenta.setId(dto.getCuentaId());
        existente.setCuenta(cuenta);

        Pago modificado = pagoRepo.save(existente);
        return entityToDto(modificado);
    }

    @Override
    public void deletePago(Long id) {
        pagoRepo.deleteById(id);
    }

    // — Mapeo manual entre DTO y entidad —
    private Pago dtoToEntity(PagoRequestDTO dto) {
        Pago p = new Pago();
        p.setMonto(dto.getMonto());
        p.setComercio(dto.getComercio());
        p.setFecha(Date.from(
                dto.getFecha().atZone(ZoneId.systemDefault()).toInstant()
        ));

        // Asocia la cuenta por su ID (sin cargar toda la entidad)
        Cuenta cuenta = new Cuenta();
        cuenta.setId(dto.getCuentaId());
        p.setCuenta(cuenta);

        return p;
    }

    private PagoRequestDTO entityToDto(Pago p) {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setId(p.getId());
        dto.setMonto(p.getMonto());
        dto.setComercio(p.getComercio());
        dto.setFecha(p.getFecha()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        );
        dto.setCuentaId(p.getCuenta() != null ? p.getCuenta().getId() : null);
        return dto;
    }
}
