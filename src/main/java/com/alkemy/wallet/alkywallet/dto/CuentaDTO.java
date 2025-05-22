package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaDTO {

    private Long id;
    private Double saldo;
    private String tipo;         // Descripción legible
    private Long usuarioId;
    private boolean deleted;

}