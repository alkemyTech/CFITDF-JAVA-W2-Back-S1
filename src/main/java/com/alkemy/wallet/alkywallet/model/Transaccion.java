package com.alkemy.wallet.alkywallet.model;gi
import java.time.LocalDate;

public class Transaccion {
    private long id;
    private LocalDate fecha;
    private double monto;
    private String descripcion;
    private TipoTransaccion tipo;
    private Usuario usuario;    // referencia al usuario que realiza la transacción
    private Cuenta cuenta;      // referencia a la cuenta afectada

    // Constructor vacío
    public Transaccion() {
    }

    // Constructor con todos los parámetros
    public Transaccion(long id, LocalDate fecha, double monto, String descripcion, TipoTransaccion tipo, Usuario usuario, Cuenta cuenta) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.usuario = usuario;
        this.cuenta = cuenta;
    }

    // Getters
    public long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    // toString para imprimir información de la transacción
    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", tipo=" + tipo +
                ", usuario=" + (usuario != null ? usuario.getId() : "null") +
                ", cuenta=" + (cuenta != null ? cuenta.getId() : "null") +
                '}';
    }
}
