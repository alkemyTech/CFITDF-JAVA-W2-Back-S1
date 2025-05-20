package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Tarjeta;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TarjetaDTO {

    private Long id;
    private String numero;
    private TipoTarjeta tipo;
    private LocalDate fechaExpiracion;

    private boolean esVirtual;

    public enum TipoTarjeta {
        CREDITO,
        DEBITO,
        PREPAGA,
    }

    private CuentaDTO cuentaDTO;

    private List<TransaccionDTO> transaccionesDTO = new ArrayList<>();
}
