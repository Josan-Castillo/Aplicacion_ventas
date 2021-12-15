package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Clientes;
import fes.aragon.modelo.FacProd;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.Productos;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NuevaFacProdController implements Initializable {

    @FXML
    private Button btnBuscarFactura;

    @FXML
    private Button btnBuscarProducto;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableColumn<Clientes, String> clienteApellido;

    @FXML
    private TableColumn<Clientes, String> clienteNombre;

    @FXML
    private TableColumn<Clientes, String> facturaFecha;

    @FXML
    private TableColumn<Facturas, Integer> facturaID;

    @FXML
    private TableColumn<Facturas, String> facturaReferencia;

    @FXML
    private TableColumn<Productos, Integer> productoID;

    @FXML
    private TableColumn<Productos, String> productoNombre;

    @FXML
    private TableColumn<Productos, Double> productoPrecio;

    @FXML
    private TableView<Facturas> tblTablaFactura;

    @FXML
    private TableView<Productos> tblTablaProducto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtFacturaBuscar;

    @FXML
    private TextField txtProductoBuscar;
    
    private FacProd facprod = new FacProd();

    @FXML
    void accionLimpiar(ActionEvent event) {
    	this.limpiar();
    }

    @FXML
    void buscarFactura(ActionEvent event) {
    	if(this.txtFacturaBuscar.getText().isEmpty() || this.txtFacturaBuscar.getText().regionMatches(0, "", 0, 1)) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa la referencia de la factura a buscar");
			alerta.showAndWait();
			this.traerDatosFact();
    	} else {
    		try {
    		Conexion cnn = new Conexion();
    		
    		this.tblTablaFactura.getItems().clear();
			this.tblTablaFactura.setItems(cnn.buscarFacturas(this.txtFacturaBuscar.getText()));
			
			if(tblTablaFactura.getItems().isEmpty()) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar la factura");
    			alerta.setContentText("La factura no existe");
    			alerta.showAndWait();
    			this.traerDatosFact();
				}
    		} catch (Exception e) {
    			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar la factura");
    			alerta.setContentText("La factura no existe");
    			alerta.showAndWait();
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void buscarProducto(ActionEvent event) {
    	if(this.txtProductoBuscar.getText().isEmpty() || this.txtProductoBuscar.getText().regionMatches(0, "", 0, 1)) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		alerta.setHeaderText("Dato incorrecto");
			alerta.setContentText("Ingresa el nombre del producto buscar");
			alerta.showAndWait();
			this.traerDatosProd();
    	} else {
    		try {
    		Conexion cnn = new Conexion();
    		
    		this.tblTablaProducto.getItems().clear();
			this.tblTablaProducto.setItems(cnn.buscarProductos(this.txtProductoBuscar.getText()));
			if(tblTablaProducto.getItems().isEmpty()) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el producto");
    			alerta.setContentText("El producto no existe");
    			alerta.showAndWait();
    			this.traerDatosProd();
				}
    		} catch (Exception e) {
    			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    			alerta.setHeaderText("Error al buscar el producto");
    			alerta.setContentText("El producto no existe");
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
			
			facprod.setCantidad(Double.parseDouble(txtCantidad.getText()));
			
			cnn.almacenarFacProd(facprod);
			
			alerta.setHeaderText("Factura producto almacenada");
    		alerta.setContentText("La factura producto se almaceno correctamente");
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
		this.facturaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.facturaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.clienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
		this.clienteApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
		
		this.productoID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.productoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.productoPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		
		this.tblTablaFactura.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Facturas fac = tblTablaFactura.getSelectionModel().getSelectedItem();
								
				facprod.setFactura(fac);
			}
		});	
		
		this.tblTablaProducto.getSelectionModel().selectedItemProperty().addListener((obj, oldSeleccion, newSeleccion) -> {
			if (newSeleccion != null) {
				Productos prod = tblTablaProducto.getSelectionModel().getSelectedItem();
								
				facprod.setProducto(prod);
			}
		});
		
		this.traerDatosFact();
		this.traerDatosProd();
	}
	
	private void traerDatosFact() {
		try {
			Conexion cnn1 = new Conexion();
			
			this.tblTablaFactura.getItems().clear();
			this.tblTablaFactura.setItems(cnn1.todasFacturas());
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
	
	private void traerDatosProd() {
		try {
			Conexion cnn2 = new Conexion();
			
			this.tblTablaProducto.getItems().clear();
			this.tblTablaProducto.setItems(cnn2.todosProductos());
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
    	this.txtCantidad.setText("");
    	this.txtProductoBuscar.setText("");
    	this.txtFacturaBuscar.setText("");
    }
}
