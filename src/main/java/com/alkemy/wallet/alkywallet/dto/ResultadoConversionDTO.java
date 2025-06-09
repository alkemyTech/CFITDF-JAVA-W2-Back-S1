package com.alkemy.wallet.alkywallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoConversionDTO {
    private CuentaDTO cuentaOrigen;  // La cuenta desde la que se restó saldo
    private CuentaDTO cuentaDestino; // La cuenta que recibió saldo en la nueva moneda
}
