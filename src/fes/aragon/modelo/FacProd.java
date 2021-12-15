package fes.aragon.modelo;

import java.time.LocalDate;

public class FacProd {
	private Double cantidad;
	Facturas factura = new Facturas();
	Productos producto = new Productos();
	
	public FacProd() {
		
	}
	
	public FacProd(Double cantidad, Facturas factura, Productos producto) {
		super();
		this.cantidad = cantidad;
		this.factura = factura;
		this.producto = producto;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Facturas getFactura() {
		return factura;
	}

	public void setFactura(Facturas factura) {
		this.factura = factura;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}
	
	//public Integer getId() {
	//	return this.factura.getId();
	//}
	
	public String getReferencia() {
		return this.factura.getReferencia();
	}
	
	//public String getNombre() {
	//	return this.factura.getNombreCliente();
	//}
	
	//public String getApellido() {
	//	return this.factura.getApellidoCliente();
	//}
	
	public LocalDate getFecha() {
		return this.factura.getFecha();
	}
	
	//public Integer getIdProd() {
	//	return this.producto.getId();
	//}
	
	public String getNombreProd() {
		return this.producto.getNombre();
	}
	
	public Double getPrecioProd() {
		return this.producto.getPrecio();
	}
}
