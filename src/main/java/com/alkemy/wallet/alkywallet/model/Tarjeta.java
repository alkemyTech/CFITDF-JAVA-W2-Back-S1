package com.alkemy.wallet.alkywallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTarjeta tipo;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @Column(name = "es_virtual")
    private boolean esVirtual;

    public enum TipoTarjeta {
        CREDITO,
        DEBITO,
        PREPAGA,
    }

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    @JsonIgnore // Ignorar esta propiedad al serializar
    private Cuenta cuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Ignorar esta propiedad al serializar
    private Usuario usuario;

    @OneToMany(mappedBy = "tarjeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();

}