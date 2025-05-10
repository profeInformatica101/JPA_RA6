package com.hlc.cliente_uno_a_muchos_pedido.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Cliente;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Pedido;
import com.hlc.cliente_uno_a_muchos_pedido.entidad.Producto;
import com.hlc.cliente_uno_a_muchos_pedido.repositorio.*;

@Component
public class InicializadorDatos implements CommandLineRunner  {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	private Faker faker = new Faker();

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().fullName());
            clienteRepository.save(cliente);
            
            for (int j = 0; j < 3; j++) {
                Pedido pedido = new Pedido();
                pedido.setFecha(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
                pedido.setDescripcion(faker.commerce().productName());
                pedido.setCantidad(faker.number().numberBetween(1, 10));
                pedido.setCliente(cliente);
                pedidoRepository.save(pedido);
                
                List<Pedido> pedidos = new ArrayList<Pedido>();
                pedidos.add(pedido);
                
                Producto producto1 = new Producto();
        		producto1.setNombre(faker.commerce().productName());
        		producto1.setDescripcion(faker.commerce().productName());
        		producto1.setPeso(faker.number().numberBetween(1, 20));
        		producto1.setStock(faker.number().numberBetween(1, 100));
        		producto1.setPedidos(pedidos);
        		productoRepository.save(producto1);
        		
        		Producto producto2 = new Producto();
        		producto2.setNombre(faker.commerce().productName());
        		producto2.setDescripcion(faker.commerce().productName());
        		producto2.setPeso(faker.number().numberBetween(1, 20));
        		producto2.setStock(faker.number().numberBetween(1, 100));
        		producto2.setPedidos(pedidos);
        		productoRepository.save(producto2);
        		
                List<Producto> productos = new ArrayList<Producto>();
                productos.add(producto1);
                productos.add(producto2);
            }
        }
	}

}
