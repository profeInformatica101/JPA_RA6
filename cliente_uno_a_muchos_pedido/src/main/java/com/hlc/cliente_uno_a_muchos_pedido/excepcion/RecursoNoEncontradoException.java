package com.hlc.cliente_uno_a_muchos_pedido.excepcion;

public class RecursoNoEncontradoException extends RuntimeException {
	// serialVersionUID se usa para asegurar la compatibilidad en la serialización de clases.
    // Permite verificar que el emisor y el receptor de un objeto serializado tienen clases compatibles.
	private static final long serialVersionUID = 1L;

	public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
