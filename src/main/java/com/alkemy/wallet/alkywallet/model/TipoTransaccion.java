package com.alkemy.wallet.alkywallet.model;

public enum TipoTransaccion {
<<<<<<< HEAD
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
=======
    DEPOSITO,
    EXTRACCION,
    TRANSFERENCIA,
    PAGO
}
>>>>>>> dev
