package com.alkemy.wallet.alkywallet.model;

import jakarta.persistence.*;
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
    private LocalDate fecha;
    private double monto;
    private String descripcion;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_transaccion_id")
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;
}
