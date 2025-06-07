package com.alkemy.wallet.alkywallet.model;

import com.alkemy.wallet.alkywallet.dto.MovimientoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static MovimientoDTO fromPago(Pago pago) {
        LocalDate fecha = pago.getFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new MovimientoDTO(
                "PAGO",
                pago.getMonto(),
                pago.getComercio(), // usamos comercio como descripción
                fecha
        );
    }


    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", monto=" + monto +
                ", comercio='" + comercio + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
