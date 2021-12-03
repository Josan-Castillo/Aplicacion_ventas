package fes.aragon.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fes.aragon.modelo.Clientes;
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
}
