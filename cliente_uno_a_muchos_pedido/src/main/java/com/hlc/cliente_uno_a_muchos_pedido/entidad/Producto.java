package com.hlc.cliente_uno_a_muchos_pedido.entidad;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
	private String nombre;
	
	@NotBlank
	@NotNull
	private String descripcion;
	
	@NotNull
	private float peso;
	
	@NotNull
	private int stock;
	
	@JoinTable(name = "producto_pedido", 
			joinColumns = @JoinColumn(name = "producto_id"), 
			inverseJoinColumns = @JoinColumn(name = "pedido_id"))
	@ManyToMany
	private List<Pedido> pedidos;

	public Producto() {
		super();
	}

	public Producto(long id, @NotNull @NotBlank(message = "El nombre de usuario no puede estar vacío") String nombre,
			@NotBlank @NotNull String descripcion, @NotNull float peso, @NotNull int stock, List<Pedido> pedidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.peso = peso;
		this.stock = stock;
		this.pedidos = pedidos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}
