package com.alkemy.wallet.alkywallet.model;

public enum TipoTransaccion {
    DEPOSITO("Depósito"),
    EXTRACCION("Extracción"),
    TRANSFERENCIA("Transferencia"),
    PAGO("Pago");

    private final String descripcion;

    private TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return descripcion;
    }
}
