package com.hlc.cliente_uno_a_muchos_pedido.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Producto;
import com.hlc.cliente_uno_a_muchos_pedido.excepcion.RecursoNoEncontradoException;
import com.hlc.cliente_uno_a_muchos_pedido.repositorio.ProductoRepository;

@Service
public class ProductoServicioImpl implements ProductoServicio{
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Producto guardarProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Producto obtenerProductoPorId(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
	}

	@Override
	public List<Producto> obtenerTodosLosProductos() {
		return productoRepository.findAll();
	}

	@Override
	public Producto actualizarProducto(Long id, Producto producto) {
		Producto existente = obtenerProductoPorId(id);
		existente.setNombre(producto.getNombre());
		existente.setDescripcion(producto.getDescripcion());
		existente.setPeso(producto.getPeso());
		existente.setStock(producto.getStock());
		existente.setPedidos(producto.getPedidos());
		return productoRepository.save(existente);
	}

	@Override
	public void eliminarProducto(Long id) {
		Producto producto = obtenerProductoPorId(id);
		productoRepository.delete(producto);
	}

	
}
