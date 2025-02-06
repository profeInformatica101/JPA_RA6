package com.hlc.usuario_uno_a_uno.errores.excepcion;

public class BadRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
        super(message);
    }
}