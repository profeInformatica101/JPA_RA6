package com.hlc.cliente_uno_a_muchos_pedido.entidad;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Table(name="pedidos")
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @Past
	private LocalDateTime fecha;
	
	@NotBlank
	@NotNull
	private String descripcion;
	
	@NotNull
	private Integer cantidad;
	
	@JoinColumn(name = "cliente_id", nullable = false)
	@ManyToOne
	private Cliente cliente;
	
	@ManyToMany(mappedBy = "pedidos")
	private List<Producto> productos;
	
    public Pedido() {}

	public Pedido(Long id, @Past LocalDateTime fecha, @NotBlank @NotNull String descripcion, @NotNull Integer cantidad,
			Cliente cliente, List<Producto> productos) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.cliente = cliente;
		this.productos = productos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
