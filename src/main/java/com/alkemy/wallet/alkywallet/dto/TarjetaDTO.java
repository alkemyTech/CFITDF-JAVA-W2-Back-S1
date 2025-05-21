package com.alkemy.wallet.alkywallet.dto;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.model.Transaccion;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TarjetaDTO {

    private Long id;
    private String numero;
    private Tarjeta.TipoTarjeta tipo;
    private LocalDate fechaExpiracion;
    private Boolean esVirtual;
    private Boolean delete;

    public enum TipoTarjeta {
        CREDITO,
        DEBITO,
        PREPAGA,
    }

    private Long cuentaDtoId;

    private List<Transaccion> transaccionesDTO = new ArrayList<>();

    public TarjetaDTO() {
    }
    public TarjetaDTO(Tarjeta tarjeta) {
        this.id = tarjeta.getId();
        this.numero = tarjeta.getNumero();
        this.tipo = tarjeta.getTipo();
        this.fechaExpiracion = tarjeta.getFechaExpiracion();
        this.esVirtual = tarjeta.getEsVirtual();
        this.cuentaDtoId = tarjeta.getCuenta().getId();
        this.transaccionesDTO = tarjeta.getTransacciones();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Tarjeta.TipoTarjeta getTipo() {
        return tipo;
    }

    public void setTipo(Tarjeta.TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean getEsVirtual() {
        return esVirtual;
    }

    public void setEsVirtual(Boolean esVirtual) {
        this.esVirtual = esVirtual;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Long getCuentaDtoId() {
        return cuentaDtoId;
    }

    public void setCuentaDtoId(Long cuentaDtoId) {
        this.cuentaDtoId = cuentaDtoId;
    }
}
