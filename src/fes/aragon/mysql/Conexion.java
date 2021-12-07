package fes.aragon.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion {
	private String url="jdbc:mysql://127.0.0.1:3306/ventas?serverTimezone=UTC";
	private String usuario="root";
	private String psw="240919";
	private Connection conexion = null;
	
	public Conexion() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(url, usuario,psw);
	}
	
	//Clientes
	
	public ObservableList<Clientes> todosClientes() throws SQLException{
		ObservableList<Clientes> lista = FXCollections.observableArrayList();
		String query = "{call todosClientes()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		
		if(!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Clientes cl = new Clientes();
				
				cl.setId(Integer.parseInt(datos.getString(1)));
				cl.setNombre(datos.getString(2));
				cl.setApellidoPaterno(datos.getString(3));
				
				lista.add(cl);
			} while(datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		
		return lista;
	}
	
	public void almacenarClientes(Clientes cliente) throws SQLException {
		String query = "{call insertarClientes(?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setString(1, cliente.getNombre());
		solicitud.setString(2, cliente.getApellidoPaterno());
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
	
	public void eliminarClientes(int id) throws SQLException {
		String query = "{call eliminarClientes(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, id);
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
	
	public void modificarClientes(Clientes cliente) throws SQLException {
		String query = "{call modificarClientes(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, cliente.getId());
		solicitud.setString(2, cliente.getNombre());
		solicitud.setString(3, cliente.getApellidoPaterno());
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
	
	//Facturas
	
	public ObservableList<Facturas> todasFacturas() throws SQLException{
		ObservableList<Facturas> lista = FXCollections.observableArrayList();
		String query = "{call todasFacturas()}";
		CallableStatement solicitud = conexion.prepareCall(query);
		ResultSet datos = solicitud.executeQuery();
		
		if(!datos.next()) {
			System.out.println("No hay datos");
		} else {
			do {
				Facturas fac = new Facturas();
				
				fac.setId(Integer.parseInt(datos.getString(1)));
				fac.setIdCliente(Integer.parseInt(datos.getString(2)));
				fac.setReferencia(datos.getString(3));
				fac.setFecha(datos.getDate(4).toLocalDate());
				
				lista.add(fac);
			} while(datos.next());
		}
		datos.close();
		solicitud.close();
		conexion.close();
		
		return lista;
	}
	
	public void eliminarFacturas(int id) throws SQLException {
		String query = "{call eliminarFacturas(?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, id);
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
	
	public void modificarFacturas(Facturas factura) throws SQLException {
		String query = "{call modificarFacturas(?,?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, factura.getId());
		solicitud.setInt(2, factura.getIdCliente());
		solicitud.setString(3, factura.getReferencia());
		solicitud.setDate(4, java.sql.Date.valueOf(factura.getFecha()));
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
	
	public void almacenarFacturas(Facturas factura) throws SQLException {
		String query = "{call insertarFacturas(?,?,?)}";
		CallableStatement solicitud = conexion.prepareCall(query);
		
		solicitud.setInt(1, factura.getIdCliente());
		solicitud.setString(2, factura.getReferencia());
		solicitud.setDate(3, java.sql.Date.valueOf(factura.getFecha()));
		solicitud.execute();

		solicitud.close();
		conexion.close();
	}
}
