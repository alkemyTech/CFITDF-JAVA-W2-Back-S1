package com.alkemy.wallet.alkywallet.model;

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
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El nombre es obligatorio")
    private String apellido;
    @NotBlank(message = "El nombre es obligatorio")
    private String email;
    @NotBlank(message = "El nombre es obligatorio")
    private String password;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private boolean borrado;
    //Relacion uno a muchos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuenta> cuentas = new ArrayList<>();
    //Relacion uno a muchos con Tarjeta
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarjeta> tarjetas = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.CLIENTE; // Valor por defecto
}