package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String status;
    private String message;
    private String id;
    private String nombre;
    private String apellido;
    private String[] cuentaIds; // Arreglo de IDs de cuentas
    private Rol rol;

    public AuthResponse(String status, String message, String id, String nombre, String apellido, String[] cuentaIds, Rol rol) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuentaIds = cuentaIds;
        this.rol = rol;
    }

    public AuthResponse(String error, String credencialesInválidas, Object o) {
    }
}
