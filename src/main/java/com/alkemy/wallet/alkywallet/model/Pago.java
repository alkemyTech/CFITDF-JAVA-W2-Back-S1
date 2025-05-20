package com.alkemy.wallet.alkywallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    private String comercio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    @JsonIgnore
    private Cuenta cuenta;

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", monto=" + monto +
                ", comercio='" + comercio + '\'' +
                ", fecha=" + fecha +
                ", cuentaId=" + (cuenta != null ? cuenta.getId() : null) +
                '}';
    }
}
