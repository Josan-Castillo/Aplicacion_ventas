package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevaFacturaController implements Initializable {

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<Clientes, String> clienteApellidoPaterno;

    @FXML
    private TableColumn<Clientes, Integer> clienteID;

    @FXML
    private TableColumn<Clientes, String> clienteNombre;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TableView<Clientes> tblTablaCliente;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtnombreClienteBuscar;
    
    private Facturas factura = new Facturas();

    @FXML
    void accionLimpiar(ActionEvent event) {
    	this.limpiar();
    }

    @FXML
    void buscarCliente(ActionEvent event) {
    	if(this.txtnombreClienteBuscar.getText().isEmpty() || this.txtnombreClienteBuscar.getText().regionMatches(0, "", 0, 1)) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa el nombre del cliente a buscar");
			alerta.showAndWait();
			this.traerDatos();
    	} else {
    		try {
    		Conexion cnn = new Conexion();
    		
    		this.tblTablaCliente.getItems().clear();
			this.tblTablaCliente.setItems(cnn.buscarClientes(this.txtnombreClienteBuscar.getText()));
			if(tblTablaCliente.getItems().isEmpty()) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el cliente");
    			alerta.setContentText("El cliente no existe");
    			alerta.showAndWait();
    			this.traerDatos();
				}
    		} catch (Exception e) {
    			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el cliente");
    			alerta.setContentText("El cliente no existe");
    			alerta.showAndWait();
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void guardarAccion(ActionEvent event) {
    	try {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			Conexion cnn = new Conexion();
			
			factura.setReferencia(txtReferencia.getText());
			factura.setFecha(pickDate.getValue());
			
			cnn.almacenarFacturas(factura);
			
			alerta.setHeaderText("Factura almacenada");
    		alerta.setContentText("La factura se almaceno correctamente");
    		limpiar();
    		      	
			alerta.showAndWait();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.clienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.clienteApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		
		this.tblTablaCliente.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Clientes cl = tblTablaCliente.getSelectionModel().getSelectedItem();
								
				factura.setCliente(cl);
			}
		});		
		
		this.traerDatos();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaCliente.getItems().clear();
			this.tblTablaCliente.setItems(cnn.todosClientes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}		
	}
	
	private void limpiar() {
    	this.txtReferencia.setText("");
    	this.pickDate.setValue(null);
    	this.txtnombreClienteBuscar.setText("");
    }
}
