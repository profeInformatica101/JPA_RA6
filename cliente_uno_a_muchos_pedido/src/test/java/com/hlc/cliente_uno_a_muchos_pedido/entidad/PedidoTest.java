package com.hlc.cliente_uno_a_muchos_pedido.entidad;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoTest {

    private Pedido pedido;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNombre("Juan Pérez");

        pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now().minusDays(1));
        pedido.setDescripcion("Pedido de prueba");
        pedido.setCantidad(3);
        pedido.setCliente(cliente);
    }

    @Test
    void testCrearPedido() {
        assertNotNull(pedido);
        assertEquals("Pedido de prueba", pedido.getDescripcion());
        assertEquals(3, pedido.getCantidad());
    }

    @Test
    void testRelacionPedidoCliente() {
        assertNotNull(pedido.getCliente());
        assertEquals("Juan Pérez", pedido.getCliente().getNombre());
    }
}