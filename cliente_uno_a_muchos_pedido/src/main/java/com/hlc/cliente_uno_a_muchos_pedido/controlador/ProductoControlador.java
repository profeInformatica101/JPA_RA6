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
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Producto;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.PedidoServicio;
import com.hlc.cliente_uno_a_muchos_pedido.servicio.ProductoServicio;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {
	
	private static final String VISTA_FORMULARIO = "productos/formulario";
    private static final String REDIRECT_LISTADO = "redirect:/productos";
    private static final String VISTA_LISTA = "productos/lista";
    private static final String VISTA_DETALLE = "productos/detalle";
    
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private ProductoServicio productoServicio;
    
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoServicio.obtenerTodosLosProducto();
        model.addAttribute("productos", productos);
        return VISTA_LISTA;
    }
    
    @GetMapping("/{id}")
    public String mostrarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return VISTA_DETALLE;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("pedidos", pedidoServicio.obtenerTodosLosPedidos());
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("producto", producto);
            model.addAttribute("pedidos", pedidoServicio.obtenerTodosLosPedidos());
            return VISTA_FORMULARIO;
        }
        productoServicio.guardarProducto(producto);
        return REDIRECT_LISTADO;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("pedidos", pedidoServicio.obtenerTodosLosPedidos());
        return VISTA_FORMULARIO;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return REDIRECT_LISTADO;
    }
}