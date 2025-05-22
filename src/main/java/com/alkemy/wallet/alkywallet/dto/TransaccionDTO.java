package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.TipoTransaccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransaccionDTO {
    private long id;
    private LocalDate fecha;
    private double monto;
    private String descripcion;
    private TipoTransaccion tipoTransaccion;
}
