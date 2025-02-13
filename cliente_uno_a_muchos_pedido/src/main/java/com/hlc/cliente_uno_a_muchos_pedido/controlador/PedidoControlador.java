package com.hlc.cliente_uno_a_muchos_pedido.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Producto;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.ClienteServicio;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.PedidoServicio;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.ProductoServicio;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos")
public class PedidoControlador {

    private static final String VISTA_FORMULARIO = "pedidos/formulario";
    private static final String REDIRECT_LISTADO = "redirect:/pedidos";

    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoServicio.obtenerTodosLosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedidos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        model.addAttribute("productos", productoServicio.obtenerTodosLosProducto());
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    public String guardarPedido(@Valid @ModelAttribute Pedido pedido, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pedido", pedido);
            model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
            model.addAttribute("productos", productoServicio.obtenerTodosLosProducto());
            return VISTA_FORMULARIO;
        }
   
        pedidoServicio.guardarPedido(pedido);
        return REDIRECT_LISTADO;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoServicio.obtenerPedidoPorId(id);
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteServicio.obtenerTodosLosClientes());
        return VISTA_FORMULARIO;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        pedidoServicio.eliminarPedido(id);
        return REDIRECT_LISTADO;
    }
}

