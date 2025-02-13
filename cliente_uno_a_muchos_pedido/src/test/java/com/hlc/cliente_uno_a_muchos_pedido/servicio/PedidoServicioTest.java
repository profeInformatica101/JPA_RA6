package com.hlc.cliente_uno_a_muchos_pedido.servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;
import com.hlc.cliente_uno_a_muchos_pedido.excepcion.RecursoNoEncontradoException;
import com.hlc.cliente_uno_a_muchos_pedido.repositorio.PedidoRepository;

class PedidoServicioImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoServicioImpl pedidoServicio;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setDescripcion("Pedido de prueba");
    }

    @Test
    void testGuardarPedido() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        Pedido resultado = pedidoServicio.guardarPedido(pedido);
        assertNotNull(resultado);
        assertEquals("Pedido de prueba", resultado.getDescripcion());
    }

    @Test
    void testObtenerPedidoPorId() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        Pedido resultado = pedidoServicio.obtenerPedidoPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerPedidoPorIdNoEncontrado() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class, () -> pedidoServicio.obtenerPedidoPorId(1L));
    }

    @Test
    void testObtenerTodosLosPedidos() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));
        List<Pedido> lista = pedidoServicio.obtenerTodosLosPedidos();
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void testActualizarPedido() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        Pedido actualizado = new Pedido();
        actualizado.setDescripcion("Pedido actualizado");
        Pedido resultado = pedidoServicio.actualizarPedido(1L, actualizado);
        assertEquals("Pedido actualizado", resultado.getDescripcion());
    }

    @Test
    void testEliminarPedido() {
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).delete(any(Pedido.class));
        assertDoesNotThrow(() -> pedidoServicio.eliminarPedido(1L));
    }
}
