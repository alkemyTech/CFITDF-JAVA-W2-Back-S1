package com.alkemy.wallet.alkywallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaRequestDTO {

    @NotNull(message = "La cuenta origen es obligatoria")
    private Long cuentaOrigenId;

    @Positive(message = "El monto debe ser mayor a cero")
    private Double monto;

    @NotBlank(message = "El CBU destino es obligatorio")
    private String cbuDestino;

    private String descripcion;
}