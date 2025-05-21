package com.alkemy.wallet.alkywallet.model;

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
    private Boolean esVirtual;

    public enum TipoTarjeta {
        CREDITO,
        DEBITO,
        PREPAGA,
    }

    @Column(nullable = false)
    private Boolean delete = false;

    @ManyToOne
    @JoinColumn(name = "cuenta")
    private Cuenta cuenta;

    @OneToMany(mappedBy = "tarjeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public Boolean getEsVirtual() {
        return esVirtual;
    }

    public Boolean getDelete() {
        return delete;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipo(TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public void setEsVirtual(Boolean esVirtual) {
        this.esVirtual = esVirtual;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}