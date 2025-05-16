package com.alkemy.wallet.alkywallet.model;

public enum TipoCuenta {

    CAJA_AHORRO("Caja de Ahorro"),
    CUENTA_CORRIENTE("Cuenta Corriente"),
    DOLAR("Cuenta en Dólares"),
    CRIPTO("Cuenta Cripto");

    private final String descripcion;

    TipoCuenta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
