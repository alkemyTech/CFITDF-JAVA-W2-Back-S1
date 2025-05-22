package com.alkemy.wallet.alkywallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PagoDTO {

    private Long id; // Este puede venir vacío cuando se crea

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;

    @NotBlank(message = "El nombre del comercio es obligatorio")
    private String comercio;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotNull(message = "La cuenta es obligatoria")
    private Long cuentaId;
}
