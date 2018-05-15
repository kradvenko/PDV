/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Articulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * @author carloscui
 */
public class IngresoMercanciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Articulo> tvArticulos;
    @FXML TableColumn<Articulo, String> tcNombre;
    @FXML TableColumn<Articulo, String> tcCodigo;
    
    @FXML TableView<Articulo> tvArticulosIngresar;
    @FXML TableColumn<Articulo, String> tcIngresarNombre;
    @FXML TableColumn<Articulo, String> tcIngresarCodigo;
    @FXML TableColumn<Articulo, String> tcIngresarCantidad;
    
    @FXML TextField tfClaveProducto;
    @FXML TextField tfNombreProducto;
    @FXML TextField tfCantidadIngresar;
    @FXML TextField tfFolioEntrega;
    
    private Articulo articuloActual;
    
    private ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    private ObservableList<Articulo> articulosEntrega = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));
        
        tcIngresarNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcIngresarCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));
        tcIngresarCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadEntrega"));       
        
    }    
    
    public void tfClaveProducto_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosCodigo(tfClaveProducto.getText());
            tvArticulos.setItems(articulos);
            tfClaveProducto.setText("");
        }
    }
    
    public void tfNombreProducto_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosNombre(tfNombreProducto.getText());
            tvArticulos.setItems(articulos);
            tfNombreProducto.setText("");
        }
    }
    
    public void tfCantidadIngresar_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {            
            if (articulos.size() > 0) {
                //articuloActual = articulos.get(0); 
               agregarArticuloLista(articuloActual);
            }
        }
    }
    
    public void agregarArticuloLista(Articulo a) {
        try {            
            a.setCantidadEntrega(Integer.parseInt(tfCantidadIngresar.getText()));
            articulosEntrega.add(a);
            tvArticulosIngresar.setItems(articulosEntrega);
            tfClaveProducto.textProperty().set("");
            tfCantidadIngresar.textProperty().set("");
            tvArticulos.getItems().clear();
        } catch (Exception exc) {
            
        }
    }
    public void eliminarArticuloLista() {
        if (articuloActual != null) {
            articulosEntrega.remove(articuloActual);
            tvArticulosIngresar.getColumns().get(0).setVisible(false);
            tvArticulosIngresar.getColumns().get(0).setVisible(true);            
        }
    }
    
    public void tvEntrega_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){                
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloActual = c;
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void IngresarMercancia() {
        try {
            if (articulosEntrega.size() > 0) {
                for (int i = 0; i < articulosEntrega.size(); i++) {
                    Articulo a = new Articulo();
                    a = articulosEntrega.get(i);
                    Articulo.actualizarExistenciaArticuloEntrega(a.getIdArticulo(), a.getCantidadEntrega(), tfFolioEntrega.getText());
                }
                articulosEntrega.clear();
                tvArticulosIngresar.getItems().clear();
            }
        } catch(Exception e) {
            
        }
    }
    
}
