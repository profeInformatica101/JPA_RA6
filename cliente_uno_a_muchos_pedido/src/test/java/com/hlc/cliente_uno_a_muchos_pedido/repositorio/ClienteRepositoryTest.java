package com.hlc.cliente_uno_a_muchos_pedido.repositorio;


import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Cliente;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;
@DataJpaTest
class ClientePedidoRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private Cliente cliente;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        cliente = clienteRepository.save(cliente);
        
        pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now().minusDays(1));
        pedido.setDescripcion("Orden de prueba");
        pedido.setCantidad(2);
        pedido.setCliente(cliente);
        
        // Asegurar que el pedido se añade a la lista de pedidos del cliente
        cliente.getPedidos().add(pedido);
        
        pedidoRepository.save(pedido);
        clienteRepository.save(cliente);
    }

    @Test
    void testGuardarCliente() {
        Cliente encontrado = clienteRepository.findById(cliente.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Juan Pérez", encontrado.getNombre());
    }

    @Test
    void testGuardarPedido() {
        Pedido encontrado = pedidoRepository.findById(pedido.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Orden de prueba", encontrado.getDescripcion());
    }

    @Test
    void testRelacionClientePedidos() {
        Cliente encontrado = clienteRepository.findById(cliente.getId()).orElse(null);
        assertNotNull(encontrado);
        List<Pedido> pedidos = encontrado.getPedidos();
        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
        assertEquals("Orden de prueba", pedidos.get(0).getDescripcion());
    }
}
