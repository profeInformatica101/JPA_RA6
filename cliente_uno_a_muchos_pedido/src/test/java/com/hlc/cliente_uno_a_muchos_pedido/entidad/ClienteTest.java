package com.hlc.cliente_uno_a_muchos_pedido.entidad;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class  ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
    }

    @Test
    void testCrearCliente() {
        assertNotNull(cliente);
        assertEquals("Juan Pérez", cliente.getNombre());
    }

    @Test
    void testAgregarPedidosACliente() {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now().minusDays(1));
        pedido.setDescripcion("Pedido de prueba");
        pedido.setCantidad(3);
        pedido.setCliente(cliente);
        
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);
        cliente.setPedidos(pedidos);
        
        assertEquals(1, cliente.getPedidos().size());
        assertEquals("Pedido de prueba", cliente.getPedidos().get(0).getDescripcion());
    }
}
