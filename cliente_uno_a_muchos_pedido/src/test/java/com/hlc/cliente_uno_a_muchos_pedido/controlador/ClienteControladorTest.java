package com.hlc.cliente_uno_a_muchos_pedido.controlador;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Cliente;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.ClienteServicio;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.PedidoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ClienteControladorTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteServicio clienteServicio;

    @Mock
    private PedidoServicio pedidoServicio;

    @InjectMocks
    private ClienteControlador clienteControlador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(clienteControlador)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void listarClientes_DeberiaRetornarVistaListaClientes() throws Exception {
        List<Cliente> clientes = Collections.singletonList(new Cliente(1L, "Juan Perez", Collections.emptyList()));
        when(clienteServicio.obtenerTodosLosClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/lista"))
                .andExpect(model().attributeExists("clientes"))
                .andExpect(model().attribute("clientes", clientes));

        verify(clienteServicio, times(1)).obtenerTodosLosClientes();
    }

    @Test
    void mostrarCliente_DeberiaRetornarVistaDetalle() throws Exception {
        Cliente cliente = new Cliente(1L, "Juan Perez", Collections.emptyList());
        when(clienteServicio.obtenerClientePorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/detalle"))
                .andExpect(model().attributeExists("cliente"))
                .andExpect(model().attribute("cliente", cliente));

        verify(clienteServicio, times(1)).obtenerClientePorId(1L);
    }

    @Test
    void mostrarPedidosPorCliente_DeberiaRetornarVistaPedidos() throws Exception {
        Cliente cliente = new Cliente(1L, "Juan Perez", Collections.emptyList());
        when(clienteServicio.obtenerClientePorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(view().name("pedidos/lista"))
                .andExpect(model().attributeExists("cliente"))
                .andExpect(model().attribute("cliente", cliente));

        verify(clienteServicio, times(1)).obtenerClientePorId(1L);
    }

    @Test
    void mostrarFormularioNuevoCliente_DeberiaRetornarVistaFormulario() throws Exception {
        mockMvc.perform(get("/clientes/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/formulario"))
                .andExpect(model().attributeExists("cliente"));
    }

    @Test
    void guardarCliente_ConDatosValidos_DeberiaRedirigir() throws Exception {
        mockMvc.perform(post("/clientes/guardar")
                        .param("id", "1")
                        .param("nombre", "Nuevo Cliente"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes"));

        verify(clienteServicio, times(1)).guardarCliente(any(Cliente.class));
    }

    @Test
    void eliminarCliente_DeberiaRedirigir() throws Exception {
        mockMvc.perform(get("/clientes/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes"));

        verify(clienteServicio, times(1)).eliminarCliente(1L);
    }
}
