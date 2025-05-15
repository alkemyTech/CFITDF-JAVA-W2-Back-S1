package com.alkemy.wallet.alkywallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String password, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired, boolean enabled) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}