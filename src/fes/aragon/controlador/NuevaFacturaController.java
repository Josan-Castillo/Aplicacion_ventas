package fes.aragon.controlador;

import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class NuevaFacturaController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtReferencia;
    
    private Facturas factura = null;

    @FXML
    void accionLimpiar(ActionEvent event) {
    		this.limpiar();
    }

    @FXML
    void guardarAccion(ActionEvent event) {
    	if(factura == null) {
    		factura = new Facturas();
    	}
    	
    	if(validar()) {
    		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    		if(factura.getId() == null) {
    			almacenar();
    			alerta.setHeaderText("Factura almacenada");
    			alerta.setContentText("La factura se almaceno correctamente");
    			limpiar();
    		} else {
    			modificar();
    			alerta.setHeaderText("Factura modificado");
    			alerta.setContentText("La factura se modifico correctamente");
    		}        	
			alerta.showAndWait();
    	}
    }
    
    private void almacenar() {
    	try {
			Conexion cnn = new Conexion();
			
			factura.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
			factura.setReferencia(txtReferencia.getText());
			factura.setFecha(pickDate.getValue());
			
			cnn.almacenarFacturas(factura);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }
    
    private void limpiar() {
    	this.txtIdCliente.setText("");
    	this.txtReferencia.setText("");
    	this.pickDate.setValue(null);
    }
    
    private boolean validar() {
    	boolean validos = true;
    	
    	if(this.txtIdCliente.getText().isEmpty() || this.txtIdCliente.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	if(this.txtReferencia.getText().isEmpty() || this.txtReferencia.getText().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	if(this.pickDate.getValue().toString().isEmpty() || this.pickDate.getValue().toString().regionMatches(0, "", 0, 1)) {
    		validos = false;
    	}
    	return validos;
    }
    
    public void modificarFactura(Facturas factura) {
    	this.factura = factura;
    	this.txtIdCliente.setText(String.valueOf(factura.getIdCliente()));
    	this.txtReferencia.setText(factura.getReferencia());
    	this.pickDate.setValue(factura.getFecha());
    }
    
    private void modificar() {
    	try {
			Conexion cnn = new Conexion();
			
			factura.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
			factura.setReferencia(txtReferencia.getText());
			factura.setFecha(pickDate.getValue());
			
			cnn.modificarFacturas(factura);
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setHeaderText("Error al guardar los datos");
			alerta.setContentText("Revisa que los datos no esten vacios o sean validos");
			alerta.showAndWait();
			e.printStackTrace();
		}
    }

}
