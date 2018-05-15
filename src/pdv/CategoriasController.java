/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Categoria;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class CategoriasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Categoria> tvCategorias;
    @FXML private TableColumn<Categoria, String> tcNombre;
    
    @FXML private TextField tfCategoriaNueva;
    @FXML private TextField tfCategoriaActualizar;
    
    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    
    private Categoria categoriaActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        cargarCategorias();
    }
    
    public void tvCategorias_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Categoria c = (Categoria) v.getSelectionModel().getSelectedItem();
                    categoriaActual = c;
                    tfCategoriaActualizar.setText(categoriaActual.getNombre());
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void cargarCategorias() {
        try {
            tvCategorias.getItems().clear();
            categorias = Categoria.obtenerListaCategorias();
            if (categorias != null) {
                tvCategorias.setItems(categorias);
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void nuevaCategoria() {
        if (tfCategoriaNueva.getText().length() > 0) {
            Categoria.agregarCategoria(tfCategoriaNueva.getText());
            cargarCategorias();
            tfCategoriaNueva.setText("");
        }
    }
    
    public void actualizarCategoria() {
        if (tfCategoriaActualizar.getText().length() > 0) {
            if (categoriaActual != null) {
                Categoria.actualizarCategoria(categoriaActual.getIdCategoria(), tfCategoriaActualizar.getText());
                cargarCategorias();
                tfCategoriaActualizar.setText("");
                categoriaActual = null;
            }
        }
    }
}