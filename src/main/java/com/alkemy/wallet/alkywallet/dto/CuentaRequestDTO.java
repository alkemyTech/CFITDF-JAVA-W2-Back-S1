package com.alkemy.wallet.alkywallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {

    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Double saldo;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private String tipo; // Esperás un valor válido del enum, por ejemplo: "CAJA_AHORRO"

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}