package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private List<Cuenta> cuentas = new ArrayList<>();

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombre, String apellido, String email, String password, List<Cuenta> cuentas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cuentas = cuentas;
    }
}
