package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.TipoTransaccion;

import java.time.LocalDate;

public class TransaccionResponseDTO {

    private Long id;
    private LocalDate fecha;
    private Double monto;
    private String descripcion;
    private TipoTransaccion tipoTransaccion;
    private Long tarjetaId;  // Solo el id de la tarjeta para evitar recursión

    // Constructor

    public TransaccionResponseDTO(Long id, LocalDate fecha, Double monto, String descripcion, TipoTransaccion tipoTransaccion, Long tarjetaId) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
        this.tipoTransaccion = tipoTransaccion;
        this.tarjetaId = tarjetaId;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Long getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Long tarjetaId) {
        this.tarjetaId = tarjetaId;
    }
}
