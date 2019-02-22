/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Articulo;
import Data.Categoria;
import Data.Unidad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class EnlazadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Articulo> tvArticulos;
    @FXML TableColumn<Articulo, String> tcNombre;
    
    @FXML ComboBox<Categoria> cbCategoria;
    
    @FXML Button btnGuardar;
    @FXML Button btnEliminar;
    @FXML Button btnCerrar;
    
    @FXML TextField tfEquivalente;
    
    @FXML Label lblEnlazadoActual;
    
    ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    
    Articulo articuloActual;
    Categoria categoriaActual;
    
    ArticulosController parent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        
        cargarCategorias();
    }
    
    public void cargarCategorias() {
        try {
            categorias = Categoria.obtenerListaCategorias();
            if (categorias != null) {
                cbCategoria.setItems(categorias);
                cbCategoria.getSelectionModel().selectFirst();
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void setParent(ArticulosController p) {
        parent = p;
    }
    
    public void setEnlace(int id, int cantidad) {
        if (id > 0) {
            Articulo a = Articulo.obtenerArticuloId(id);
            categoriaActual = Categoria.obtenerCategoria(a.getIdCategoria());
            cbCategoria.setValue(categoriaActual);
            tfEquivalente.setText(String.valueOf(cantidad));
            cargarArticulos();
            tvArticulos.getSelectionModel().select(a);
            lblEnlazadoActual.setText("Artículo enlazado actual: " + a.getNombre());
            articuloActual = a;
        } else {
            cargarArticulos();
        }
    }
    
    public void cargarArticulos() {
        try {
            categoriaActual = cbCategoria.getValue();
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosCategoria(categoriaActual.getIdCategoria());
            tvArticulos.setItems(articulos);
        } catch (Exception exc) {
            
        }
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void guardar() {
        parent.setEnlace(articuloActual.getIdArticulo(), Integer.parseInt(tfEquivalente.getText()));
        cerrar();
    }
    
    public void eliminarEnlace() {
        parent.setEnlace(0, 0);
        cerrar();
    }
    
    public void tvArticulos_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloActual = c;
                    lblEnlazadoActual.setText("Artículo enlazado actual: " + articuloActual.getNombre());
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
}