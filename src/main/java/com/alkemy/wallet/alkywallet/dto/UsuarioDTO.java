package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Rol;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String DNI;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Rol rol;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaRegistro;
    private boolean enabled = true;
    private List<Cuenta> cuentas = new ArrayList<>();
}
