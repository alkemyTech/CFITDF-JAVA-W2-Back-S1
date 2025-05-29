package com.alkemy.wallet.alkywallet.model;

import jakarta.persistence.*;
<<<<<<< HEAD
import jakarta.validation.constraints.*;
=======
import jakarta.validation.constraints.NotNull;
>>>>>>> dev
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD

=======
>>>>>>> dev
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
<<<<<<< HEAD
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor positivo")
    private Double monto;

    @NotBlank(message = "La descripción es obligatoria")
=======
    private LocalDate fecha;

    private double monto;

>>>>>>> dev
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id")
<<<<<<< HEAD
    @NotNull(message = "La tarjeta es obligatoria")
    private Tarjeta tarjeta;
}
=======
    private Tarjeta tarjeta;
}
>>>>>>> dev
