package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.ConversionMonedaRequestDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.ResultadoConversionDTO;
import com.alkemy.wallet.alkywallet.exception.BadRequestException;
import com.alkemy.wallet.alkywallet.exception.ResourceNotFoundException;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.TipoCuenta;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements IConversionService {

    private final ICuentaRepository cuentaRepository;

    private static final double DOLAR_A_PESO = 1190.0;

    @Transactional
    @Override
    public ResultadoConversionDTO convertirMoneda(ConversionMonedaRequestDTO dto) {
        Cuenta origen = cuentaRepository.findByIdAndDeletedFalse(dto.getCuentaOrigenId());
        if (origen == null) {
            throw new ResourceNotFoundException("Cuenta origen no encontrada");
        }

        TipoCuenta tipoDestino = (origen.getTipo() == TipoCuenta.DOLAR) ? TipoCuenta.CAJA_AHORRO : TipoCuenta.DOLAR;

        Cuenta destino = cuentaRepository.findByUsuarioIdAndTipoAndDeletedFalse(
                origen.getUsuario().getId(), tipoDestino
        ).orElseThrow(() -> new BadRequestException(
                "El usuario no tiene cuenta en " + tipoDestino.name()));

        // Validar fondos suficientes
        if (dto.getMonto() <= 0) {
            throw new BadRequestException("El monto debe ser mayor a cero");
        }

        if (origen.getSaldo() < dto.getMonto()) {
            throw new BadRequestException("Saldo insuficiente en la cuenta origen");
        }

        double montoConvertido;
        if (origen.getTipo() == TipoCuenta.CAJA_AHORRO || origen.getTipo() == TipoCuenta.CUENTA_CORRIENTE) {
            montoConvertido = dto.getMonto() / DOLAR_A_PESO;
        } else {
            montoConvertido = dto.getMonto() * DOLAR_A_PESO;
        }

        // Actualizar saldos
        origen.setSaldo(origen.getSaldo() - dto.getMonto());
        destino.setSaldo(destino.getSaldo() + montoConvertido);

        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        return new ResultadoConversionDTO(new CuentaDTO(origen), new CuentaDTO(destino));
    }
}

