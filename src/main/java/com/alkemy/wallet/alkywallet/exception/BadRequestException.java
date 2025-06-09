package com.alkemy.wallet.alkywallet.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
