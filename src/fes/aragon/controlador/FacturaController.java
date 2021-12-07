package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Facturas;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class FacturaController implements Initializable {

    @FXML
    private TableColumn<Facturas, Integer> clienteID;

    @FXML
    private TableColumn<Facturas, String> comando;

    @FXML
    private TableColumn<Facturas, Date> facturaFecha;

    @FXML
    private TableColumn<Facturas, Integer> facturaID;

    @FXML
    private TableColumn<Facturas, String> facturaReferencia;

    @FXML
    private TableView<Facturas> tblTablaFactura;

    @FXML
    void nuevaFactura(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFactura.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFactura.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void refrescarFact(MouseEvent event) {
    	this.traerDatosFact();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.facturaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
		this.facturaReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.facturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
				
		Callback<TableColumn<Facturas, String>, TableCell<Facturas, String>>
		celda = (TableColumn<Facturas, String> parametros) -> {
			final TableCell<Facturas, String> cel = new TableCell<Facturas, String> () {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					if(arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
								
						borrarIcono.setGlyphStyle("-fx-fill:#F97255;-glyph-size:25px;-fx-cursor:hand;");
						modificarIcono.setGlyphStyle("-fx-fill:#F97255;-glyph-size:25px;-fx-cursor:hand;");
								
						borrarIcono.setOnMouseClicked((MouseEvent evento)->{
							Facturas factura = tblTablaFactura.getSelectionModel().getSelectedItem();
							
							borrarFact(factura.getId());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento)->{
							Facturas factura = tblTablaFactura.getSelectionModel().getSelectedItem();
									
							modificarFactura(factura);
						});
						HBox hbox = new HBox(borrarIcono, modificarIcono);
						hbox.setStyle("-fx-alignment:center");
						HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
						HBox.setMargin(modificarIcono, new Insets(2, 3, 0, 2));
						setGraphic(hbox);
						setText(null);
					}
				}				
			};
			return cel;
		};
		this.comando.setCellFactory(celda);
		this.traerDatosFact();
	}

	private void traerDatosFact() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaFactura.getItems().clear();
			this.tblTablaFactura.setItems(cnn.todasFacturas());
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
	
	private void borrarFact(int id) {
		try {
			Conexion cnn = new Conexion();
			
			cnn.eliminarFacturas(id);
			this.traerDatosFact();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void modificarFactura(Facturas factura) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/NuevaFactura.fxml"));
			Parent parent = (Parent)alta.load();
			((NuevaFacturaController)alta.getController()).modificarFactura(factura);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFactura.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
