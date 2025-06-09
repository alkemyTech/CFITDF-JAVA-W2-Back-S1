package com.alkemy.wallet.alkywallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    private Long id;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;

    @NotBlank(message = "El nombre del comercio es obligatorio")
    private String comercio;

    @NotNull(message = "La fecha es obligatoria")
    // Se espera que Jackson reciba un ISO-8601 compatible, p.ej. "2025-06-01T14:30:00"
    private LocalDateTime fecha;

    @NotNull(message = "La cuenta es obligatoria")
    private Long cuentaId;
}
