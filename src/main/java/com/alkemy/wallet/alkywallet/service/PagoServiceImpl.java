package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.PagoRequestDTO;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Pago;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import com.alkemy.wallet.alkywallet.repository.IPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepo;

    @Autowired
    private ICuentaRepository cuentaRepo;

    @Override
    public List<PagoRequestDTO> getAllPagos() {
        return pagoRepo.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PagoRequestDTO getPagoById(Long id) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado (ID=" + id + ")"));
        return entityToDto(pago);
    }

    @Override
    @Transactional
    public PagoRequestDTO createPago(PagoRequestDTO dto) {
        // 1) Obtener la cuenta desde la base de datos
        Long cuentaId = dto.getCuentaId();
        Cuenta cuenta = cuentaRepo.findById(cuentaId)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada (ID=" + cuentaId + ")"));

        // 2) Verificar saldo suficiente
        Double montoPago = dto.getMonto();
        if (cuenta.getSaldo() == null || cuenta.getSaldo() < montoPago) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta. Saldo actual: " +
                    formatearSaldo(cuenta.getSaldo()));
        }

        // 3) Restar el monto de la cuenta
        double nuevoSaldo = cuenta.getSaldo() - montoPago;
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepo.save(cuenta); // Persiste el nuevo saldo

        // 4) Crear y guardar la entidad Pago
        Pago entidad = new Pago();
        entidad.setMonto(dto.getMonto());
        entidad.setComercio(dto.getComercio());
        entidad.setFecha(Date.from(dto.getFecha().atZone(ZoneId.systemDefault()).toInstant()));

        // Asociar la cuenta completa (no solo el ID) para que JPA relacione bien la foreign key
        entidad.setCuenta(cuenta);

        Pago guardado = pagoRepo.save(entidad);

        // 5) Devolver el DTO correspondiente
        return entityToDto(guardado);
    }

    @Override
    @Transactional
    public PagoRequestDTO updatePago(Long id, PagoRequestDTO dto) {
        Pago existente = pagoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado (ID=" + id + ")"));

        // Si cambias el monto en edición, podrías tener que ajustar el saldo de la cuenta original.
        // Para simplificar este ejemplo, asumimos que no vas a editar montos (o que ya lo controlas por separado).
        existente.setMonto(dto.getMonto());
        existente.setComercio(dto.getComercio());
        existente.setFecha(Date.from(dto.getFecha().atZone(ZoneId.systemDefault()).toInstant()));

        // Si cambias la cuenta asociada, podrías mover el registro a otra cuenta. Aquí lo cubrimos:
        if (!existente.getCuenta().getId().equals(dto.getCuentaId())) {
            Cuenta nuevaCuenta = cuentaRepo.findById(dto.getCuentaId())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta nueva no encontrada (ID=" + dto.getCuentaId() + ")"));
            existente.setCuenta(nuevaCuenta);
        }

        Pago modificado = pagoRepo.save(existente);
        return entityToDto(modificado);
    }

    @Override
    @Transactional
    public void deletePago(Long id) {
        // (Opcional) Si quieres “devolver” el dinero a la cuenta al eliminar un pago, tendrías que readicionar el monto.
        // En este ejemplo solo borramos el pago sin modificar el saldo.
        pagoRepo.deleteById(id);
    }

    // — Mapeo manual entre Pago (entidad) y PagoRequestDTO (así lo estabas usando) —
    private Pago dtoToEntity(PagoRequestDTO dto) {
        Pago p = new Pago();
        p.setMonto(dto.getMonto());
        p.setComercio(dto.getComercio());
        p.setFecha(Date.from(dto.getFecha().atZone(ZoneId.systemDefault()).toInstant()));

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
        dto.setCuentaId(p.getCuenta().getId());
        return dto;
    }

    // Utilidad para formatear saldo en caso de excepciones
    private String formatearSaldo(Double valor) {
        if (valor == null) return "0,00";
        // Ejemplo para formatear con separador de miles y coma decimal:
        return String.format("%,.2f", valor).replace(",", "X").replace(".", ",").replace("X", ".");
    }
}
