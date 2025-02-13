package com.hlc.cliente_uno_a_muchos_pedido.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlc.cliente_uno_a_muchos_pedido.entidad.Cliente;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;
import com.hlc.cliente_uno_a_muchos_pedido.excepcion.RecursoNoEncontradoException;
import com.hlc.cliente_uno_a_muchos_pedido.repositorio.ClienteRepository;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente existente = obtenerClientePorId(id);
        existente.setNombre(cliente.getNombre());
        return clienteRepository.save(existente);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerClientePorId(id);
        clienteRepository.delete(cliente);
    }

	@Override
	public List<Pedido> obtenerPedidosPorCliente(Cliente cliente) {
		return cliente.getPedidos();
	}
}