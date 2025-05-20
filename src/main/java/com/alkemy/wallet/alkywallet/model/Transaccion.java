package com.alkemy.wallet.alkywallet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor positivo")
    private Double monto;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id")
    @NotNull(message = "La tarjeta es obligatoria")
    private Tarjeta tarjeta;
}
