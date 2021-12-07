package fes.aragon.modelo;

import java.time.LocalDate;

public class Facturas {
	private Integer id;
	private Integer idCliente;
	private String referencia;
	private LocalDate fecha;
	
	public Facturas() {
		// TODO Auto-generated constructor stub
	}

	public Facturas(Integer id, Integer idCliente, String referencia, LocalDate fecha) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.referencia = referencia;
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
}
