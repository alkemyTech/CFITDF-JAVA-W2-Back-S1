package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private List<Cuenta> cuentas = new ArrayList<>();
    private List<Tarjeta> tarjetas = new ArrayList<>();
}
