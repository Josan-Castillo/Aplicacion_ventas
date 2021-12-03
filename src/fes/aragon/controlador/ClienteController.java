package fes.aragon.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Clientes;
import fes.aragon.mysql.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ClienteController implements Initializable {
	
	@FXML
	private TableView<Clientes> tblTablaCliente;

    @FXML
    private TableColumn<Clientes, String> clienteApellidoPaterno;

    @FXML
    private TableColumn<Clientes, Integer> clienteID;

    @FXML
    private TableColumn<Clientes, String> clienteNombre;

    @FXML
    private TableColumn<Clientes, String> comando;

    @FXML
    void nuevoCliente(MouseEvent event) {

    }

    @FXML
    void refrescar(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.clienteID.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.clienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.clienteApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
		
		Callback<TableColumn<Clientes, String>, TableCell<Clientes, String>>
		celda = (TableColumn<Clientes, String> parametros) -> {
			final TableCell<Clientes, String> cel = new TableCell<Clientes, String> () {

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					if(arg1) {
						setGraphic(null);
						setText(null);
					} else {
						FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
						
						borrarIcono.setStyle("-fx-cursor:hand;"
						+"-glyph-size:25px;"
								+"-fx-fill:#F97255");
						modificarIcono.setStyle("-fx-cursor:hand;"
								+"-glyph-size:25px;"
										+"-fx-fill:#F97255");
						
						borrarIcono.setOnMouseClicked((MouseEvent evento)->{
							System.out.println("Evento Borrar");	
						});
						modificarIcono.setOnMouseClicked((MouseEvent evento)->{
							System.out.println("Evento Modificar");	
						});
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
}
