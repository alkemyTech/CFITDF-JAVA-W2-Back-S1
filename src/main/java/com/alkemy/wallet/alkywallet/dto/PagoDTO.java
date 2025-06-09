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

    private Long id;

    private Double monto;

    private String comercio;

    private LocalDateTime fecha;

    private Long cuentaId;
}
