/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Apartado;
import Data.Articulo;
import Data.Categoria;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class ConvertidorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField tfCajetillas;
    
    @FXML private TextField tfFiltroCajetillas;
    
    @FXML private TableView<Articulo> tvCajetillas;
    @FXML private TableColumn tcCajetilla;
    
    @FXML private Label lblArticulo;
    @FXML private Label lblCantidad;
    
    ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    
    Articulo articuloActual;
    Articulo cigarroActual;
    
    @FXML Button btnGuardar;
    @FXML Button btnCerrar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCajetilla.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        
        cargarArticulos();
    }
    
    public void Convertir() {
        Integer cajetillas = 0;
        Integer cigarros = 0;
        try {
            if (articuloActual == null) {
                return;
            }            
            if (tfCajetillas.getText().length() > 0) {
                cajetillas = Integer.parseInt(tfCajetillas.getText());
            } else {
                return;
            }
            cigarros = articuloActual.getCantidadEnlazado() * cajetillas;
            Articulo.actualizarExistenciaArticulo(articuloActual.getIdArticulo(), cajetillas);
            Articulo.actualizarExistenciaArticulo(articuloActual.getIdArticuloEnlazado(), cigarros * -1);
            cerrar();
        } catch (Exception exc) {
            
        }
    }
    
    public void tvCajetillas_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo a = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloActual = a;
                    lblArticulo.setText(Articulo.obtenerArticuloId(a.getIdArticuloEnlazado()).getNombre());
                    checarCantidad();
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void tvCigarros_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo a = (Articulo) v.getSelectionModel().getSelectedItem();
                    cigarroActual = a;
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void cargarArticulos() {
        try {
            tvCajetillas.getItems().clear();
            articulos = Articulo.obtenerArticulosNombreCategoria("CIGARROS", "%");
            tvCajetillas.setItems(articulos);            
        } catch (Exception exc) {
            
        }
    }
    
    public void tfFiltroCajetillas_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvCajetillas.getItems().clear();
            if (tfFiltroCajetillas.getText().length() > 0) {
                articulos = Articulo.obtenerArticulosNombreCategoria("Cigarros", "%" + tfFiltroCajetillas.getText() + "%");
            } else {
                articulos = Articulo.obtenerArticulosNombreCategoria("Cigarros", "%");
            }
            tvCajetillas.setItems(articulos);            
        }
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void tfEquivalente_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checarCantidad();
        }
    }
    
    public void checarCantidad() {
        try {
            int cantidad = Integer.parseInt(tfCajetillas.getText());
            cantidad = cantidad * articuloActual.getCantidadEnlazado();
            lblCantidad.setText(String.valueOf(cantidad));
        } catch (Exception ex) {
            tfCajetillas.setText("1");
            lblCantidad.setText(String.valueOf(articuloActual.getCantidadEnlazado()));
        }
    }
}