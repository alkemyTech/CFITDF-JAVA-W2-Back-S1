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

    // No es necesario incluir el ID aquí si nunca lo recibes en la creación,
    // pero lo dejamos por si en PUT deseas enviar el id en el body.
    private Long id;

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
