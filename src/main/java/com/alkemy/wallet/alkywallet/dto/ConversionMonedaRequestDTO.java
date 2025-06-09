package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class ConversionMonedaRequestDTO {
    @NotNull(message = "La cuenta origen es obligatoria")
    private Long cuentaOrigenId;

    @Positive(message = "El monto debe ser mayor a cero")
    private Double monto;

    @NotNull(message = "Debe especificar el tipo de cuenta destino")
    private TipoCuenta tipoDestino;
}


