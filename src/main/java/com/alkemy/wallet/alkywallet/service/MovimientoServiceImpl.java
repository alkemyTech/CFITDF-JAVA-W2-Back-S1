package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.MovimientoDTO;
import com.alkemy.wallet.alkywallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Pago;
import com.alkemy.wallet.alkywallet.model.Transaccion;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements IMovimientoService {

    private final ICuentaRepository cuentaRepository;

    @Override
    public List<MovimientoDTO> obtenerMovimientosPorCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findByIdAndDeletedFalse(cuentaId);
        if (cuenta == null) {
            throw new ResourceNotFoundException("Cuenta no encontrada");
        }

        List<MovimientoDTO> movimientos = new ArrayList<>();

        // Mapear pagos
        for (Pago pago : cuenta.getPagos()) {
            LocalDate fecha = pago.getFecha().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            movimientos.add(new MovimientoDTO(
                    "PAGO",
                    pago.getMonto(),
                    pago.getComercio(),
                    fecha
            ));
        }

        // Mapear transacciones
        for (Transaccion transaccion : cuenta.getTransacciones()) {
            movimientos.add(new MovimientoDTO(
                    "TRANSACCION",
                    transaccion.getMonto(),
                    transaccion.getDescripcion(),
                    transaccion.getFecha() // ya es LocalDate
            ));
        }

        // Ordenar por fecha descendente
        movimientos.sort(Comparator.comparing(MovimientoDTO::getFecha).reversed());

        return movimientos;
    }
}
