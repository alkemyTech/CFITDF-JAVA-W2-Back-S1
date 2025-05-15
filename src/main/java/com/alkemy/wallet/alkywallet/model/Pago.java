package com.alkemy.wallet.alkywallet.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    private String comercio;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    // --- Constructores ---
    public Pago() { }

    public Pago(Double monto, String comercio, LocalDateTime fecha, Cuenta cuenta) {
        this.monto = monto;
        this.comercio = comercio;
        this.fecha = fecha;
        this.cuenta = cuenta;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    // --- toString, equals y hashCode (opcional) ---
    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", monto=" + monto +
                ", comercio='" + comercio + '\'' +
                ", fecha=" + fecha +
                ", cuentaId=" + (cuenta != null ? cuenta.getId() : null) +
                '}';
    }
}