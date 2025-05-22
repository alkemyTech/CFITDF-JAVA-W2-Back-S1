package com.alkemy.wallet.alkywallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenCuentaDTO {

    private Long cuentaId;
    private Double saldoActual;
    private Double totalDepositado;
    private Double totalExtraido;
    private Double totalTransferido;
    private Double totalPagado;
    private Integer cantidadTransacciones;
    private LocalDate fechaUltimaTransaccion;
}
