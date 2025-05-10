package com.hlc.cliente_uno_a_muchos_pedido.entidad;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "clientes")
public class Cliente {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
	private String nombre;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<Pedido>();
    
	public Cliente() {}

	public Cliente(Long id, @NotNull @NotBlank(message = "El nombre de usuario no puede estar vacío") String nombre,
			List<Pedido> pedidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pedidos = pedidos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}


	
}
