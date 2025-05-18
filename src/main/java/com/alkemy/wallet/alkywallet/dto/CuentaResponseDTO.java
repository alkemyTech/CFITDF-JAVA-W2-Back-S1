package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDTO {

    private Long id;                      // Lo mantenemos para futuras operaciones
    private Double saldo;
    private String tipo;                  // Descripción legible del enum
    private String nombreUsuario;
    private String apellidoUsuario;

    public CuentaResponseDTO(Cuenta cuenta) {
        this.id = cuenta.getId();
        this.saldo = cuenta.getSaldo();
        this.tipo = cuenta.getTipo().getDescripcion();
        this.nombreUsuario = cuenta.getUsuario().getNombre();
        this.apellidoUsuario = cuenta.getUsuario().getApellido();
    }
}