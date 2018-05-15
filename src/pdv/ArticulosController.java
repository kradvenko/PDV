/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Articulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Data.Categoria;
import Data.Conexion;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class ArticulosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Categoria> tvCategorias;
    @FXML TableColumn<Categoria, String> tcCategoria;
    
    @FXML TableView<Articulo> tvArticulos;
    @FXML TableColumn<Articulo, String> tcNombre;
    @FXML TableColumn<Articulo, String> tcCodigo;
    @FXML TableColumn<Articulo, String> tcExistencia;
    @FXML TableColumn<Articulo, String> tcPrecio;
    
    @FXML TextField tfNombre;
    @FXML TextField tfCodigo;
    @FXML TextField tfExistencia;
    @FXML TextField tfPrecio;
    @FXML TextField tfBusquedaCodigo;
    @FXML TextField tfBusquedaNombre;
    
    @FXML ComboBox<Categoria> cbCategoria;
    
    @FXML Button btNuevo;
    @FXML Button btActualizar;
    
    @FXML Label lblTotalArticulos;
    @FXML Label lblTotalEfectivo;
    @FXML Label lblArticulosTienda;
    @FXML Label lblTotalTienda;
    
    @FXML Pane pDatosArticulo;
    
    ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    
    Categoria categoriaActual;
    Articulo articuloActual;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCategoria.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));
        tcExistencia.setCellValueFactory(new PropertyValueFactory<Articulo, String>("existencia"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        
        cargarCategorias();
        calcularTotalTienda();
    }
    
    public void tvCategorias_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Categoria c = (Categoria) v.getSelectionModel().getSelectedItem();
                    categoriaActual = c;
                    cargarArticulos();
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
                
                cbCategoria.setItems(categorias);
                cbCategoria.getSelectionModel().selectFirst();
                cbCategoria.setCellFactory(new Callback<ListView<Categoria>,ListCell<Categoria>>(){
                        @Override
                        public ListCell<Categoria> call(ListView<Categoria> p) {
                            final ListCell<Categoria> cell = new ListCell<Categoria>(){
                                @Override
                                protected void updateItem(Categoria t, boolean bln) {
                                    super.updateItem(t, bln);
                                    if(t != null){
                                        setText(t.getNombre());
                                    }else{
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    });
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void cargarArticulos() {
        try {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosCategoria(categoriaActual.getIdCategoria());
            tvArticulos.setItems(articulos);
            calcularTotalCategoria();
        } catch (Exception exc) {
            
        }
    }
    
    public void tvArticulos_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloActual = c;
                    tfNombre.setText(c.getNombre());
                    tfCodigo.setText(c.getCodigo());
                    tfExistencia.setText(String.valueOf(c.getExistencia()));
                    tfPrecio.setText(String.valueOf(c.getPrecio()));
                    categoriaActual = Categoria.obtenerCategoria(c.getIdCategoria());
                    tvCategorias.getSelectionModel().select(categoriaActual);
                    cbCategoria.setValue(categoriaActual);
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void nuevoArticulo() {
        if (btNuevo.getText().contains("Nuevo")) {
            tfNombre.setText("");
            tfCodigo.setText("-");
            tfExistencia.setText("0");
            tfPrecio.setText("0");
            cbCategoria.setValue(categoriaActual);
            btNuevo.setText("Guardar");
            btActualizar.setText("Cancelar");
        } else {
            try 
            {
                if (tfNombre.getText().length() <= 0) {
                    return;
                }
                Integer.parseInt(tfExistencia.getText());
                Float.parseFloat(tfPrecio.getText());
                
                String nombre = tfNombre.getText();
                int existencia = Integer.parseInt(tfExistencia.getText());
                Float precio = Float.parseFloat(tfPrecio.getText());
                String codigo = tfCodigo.getText();
                Categoria c = cbCategoria.getValue();
                int idCategoria = c.getIdCategoria();               
                
                Articulo.nuevoArticulo(nombre, codigo, existencia, idCategoria, precio, "NUEVO");
                
                btNuevo.setText("Nuevo");
                btActualizar.setText("Actualizar");
                tfNombre.setText("");
                tfCodigo.setText("");
                tfExistencia.setText("");
                tfPrecio.setText("");
                
                cargarArticulos();
            } catch (NumberFormatException exc) {
                
            }
        }
    }
    
    public void actualizarArticulo() {
        if (btActualizar.getText().contains("Actualizar")) {
            if(articuloActual != null) {
                try 
                {
                    if (tfNombre.getText().length() <= 0) {
                        return;
                    }
                    Integer.parseInt(tfExistencia.getText());
                    Float.parseFloat(tfPrecio.getText());

                    String nombre = tfNombre.getText();
                    int existencia = Integer.parseInt(tfExistencia.getText());
                    Float precio = Float.parseFloat(tfPrecio.getText());
                    String codigo = tfCodigo.getText();
                    Categoria c = cbCategoria.getValue();
                    int idCategoria = c.getIdCategoria();
                    int idArticulo = articuloActual.getIdArticulo();
                    
                    Articulo.actualizarArticulo(idArticulo, nombre, codigo, existencia, idCategoria, precio, existencia - articuloActual.getExistencia(),"ACTUALIZACION");
                    
                    tfNombre.setText("");
                    tfCodigo.setText("");
                    tfExistencia.setText("");
                    tfPrecio.setText("");

                    cargarArticulos();
                } catch (NumberFormatException exc) {

                }
            }
        } else {
            btNuevo.setText("Nuevo");
            btActualizar.setText("Actualizar");
            tfNombre.setText("");
            tfCodigo.setText("");
            tfExistencia.setText("");
            tfPrecio.setText("");
        }
    }
    
    public void calcularTotalCategoria() {
        Float totalEfectivo = 0f;
        int totalArticulos = 0;
        
        for (int i = 0; i < articulos.size(); i++) {
            Articulo a = articulos.get(i);
            totalEfectivo = totalEfectivo + a.getPrecio() * a.getExistencia();
            totalArticulos = totalArticulos + a.getExistencia();
        }
        
        lblTotalArticulos.setText(String.valueOf(totalArticulos));
        lblTotalEfectivo.setText("$ " + String.valueOf(totalEfectivo));
    }
    
    public void calcularTotalTienda() {
        int totalTienda = 0;
        int totalArticulos = 0;
        totalTienda = Articulo.calcularTotalTienda();
        totalArticulos = Articulo.calcularArticulosTienda();
        lblArticulosTienda.setText(String.valueOf(totalArticulos));
        lblTotalTienda.setText(String.valueOf(totalTienda));
    }
    
    public void OcultarDatosArticulo()
    {
        pDatosArticulo.visibleProperty().setValue(Boolean.FALSE);
    }
    
    public void tfBusquedaCodigo_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosCodigo(tfBusquedaCodigo.getText());
            tvArticulos.setItems(articulos);
            tfBusquedaCodigo.setText("");
        }
    }
    
    public void tfBusquedaNombre_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosNombre(tfBusquedaNombre.getText());
            tvArticulos.setItems(articulos);
            tfBusquedaNombre.setText("");
        }
    }
}
