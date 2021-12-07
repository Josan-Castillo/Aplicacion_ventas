package fes.aragon.modelo;

public class Productos {
	private Integer id;
	private String nombre;
	private Double precio;
	
	public Productos() {
		// TODO Auto-generated constructor stub
	}
	
	public Productos(Integer id, String nombre, Double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
