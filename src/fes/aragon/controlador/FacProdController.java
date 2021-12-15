package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.FacProd;
import fes.aragon.modelo.Facturas;
import fes.aragon.modelo.Productos;
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

public class FacProdController implements Initializable {

    @FXML
    private TableColumn<FacProd, Integer> cantidadFacProd;

    @FXML
    private TableColumn<FacProd, String> comando;

    @FXML
    private TableColumn<Facturas, Date> fechaFactura;

    @FXML
    private TableColumn<Productos, String> nombreProducto;

    @FXML
    private TableColumn<Productos, Double> precioProducto;

    @FXML
    private TableColumn<Facturas, String> referenciaFactura;

    @FXML
    private TableView<FacProd> tblTablaFacProd;

    @FXML
    void nuevaFacProd(MouseEvent event) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/vista/NuevaFacProd.fxml"));
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacProd.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void refrescar(MouseEvent event) {
    	this.traerDatos();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.referenciaFactura.setCellValueFactory(new PropertyValueFactory<>("referencia"));
		this.fechaFactura.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.nombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProd"));
		this.precioProducto.setCellValueFactory(new PropertyValueFactory<>("precioProd"));
		this.cantidadFacProd.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
				
		Callback<TableColumn<FacProd, String>, TableCell<FacProd, String>>
		celda = (TableColumn<FacProd, String> parametros) -> {
			final TableCell<FacProd, String> cel = new TableCell<FacProd, String> () {

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
							FacProd facprod = tblTablaFacProd.getSelectionModel().getSelectedItem();
							
							borrar(facprod.getFactura().getId(), facprod.getProducto().getId());
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento)->{
							FacProd facprod = tblTablaFacProd.getSelectionModel().getSelectedItem();
									
							modificarFacProd(facprod);
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
		this.traerDatos();
	}
	
	private void traerDatos() {
		try {
			Conexion cnn = new Conexion();
			this.tblTablaFacProd.getItems().clear();
			this.tblTablaFacProd.setItems(cnn.todosFacProd());
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
	
	private void borrar(int id_fac, int id_prod) {
		try {
			Conexion cnn = new Conexion();
			
			cnn.eliminarFacProd(id_fac, id_prod);
			this.traerDatos();
		} catch (Exception e) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setTitle("Problema en B.D");
			alerta.setHeaderText("Error en la aplicacion");
			alerta.setContentText("Consulta al fabricante, por favor.");
			alerta.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void modificarFacProd(FacProd facprod) {
		try {
			FXMLLoader alta = new FXMLLoader(getClass().getResource("/fes/aragon/vista/ModificarFacProd.fxml"));
			Parent parent = (Parent)alta.load();
			((ModificarFacProdController)alta.getController()).modificarFacProd(facprod);
			Scene escena = new Scene(parent);
			Stage escenario = new Stage();
			
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.initOwner(tblTablaFacProd.getScene().getWindow());
			escenario.setScene(escena);
			escenario.initStyle(StageStyle.UTILITY);
			escenario.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
