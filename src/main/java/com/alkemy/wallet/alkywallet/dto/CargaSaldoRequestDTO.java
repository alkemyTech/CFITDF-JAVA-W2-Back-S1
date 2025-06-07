package com.alkemy.wallet.alkywallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CargaSaldoRequestDTO {

    @NotNull(message = "El ID de la cuenta no puede ser nulo")
    private Long cuentaId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;
}
