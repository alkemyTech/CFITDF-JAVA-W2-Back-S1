package com.alkemy.wallet.alkywallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiarContraseñaDTO {
    private String contrasenaActual;
    private String nuevaContrasena;
}
