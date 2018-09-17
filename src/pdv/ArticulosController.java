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
import Data.Unidad;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
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
    @FXML TextField tfCosto;
    @FXML TextField tfBusquedaCodigo;
    @FXML TextField tfBusquedaNombre;
    
    @FXML ComboBox<Categoria> cbCategoria;
    @FXML ComboBox<Unidad> cbUnidad;
    
    @FXML Button btNuevo;
    @FXML Button btActualizar;
    @FXML Button btEnlazado;
    
    @FXML Label lblTotalArticulos;
    @FXML Label lblTotalEfectivo;
    @FXML Label lblArticulosTienda;
    @FXML Label lblTotalTienda;
    
    @FXML Pane pDatosArticulo;
    
    ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    ObservableList<Unidad> unidades = FXCollections.observableArrayList();
    
    Categoria categoriaActual;
    Articulo articuloActual;
    
    String tipo;
    
    int idArticuloEnlazado;
    int cantidadEnlazado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCategoria.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));
        tcExistencia.setCellValueFactory(new PropertyValueFactory<Articulo, String>("existencia"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        
        cargarCategorias();
        cargarUnidades();
        calcularTotalTienda();
        
        idArticuloEnlazado = 0;
        cantidadEnlazado = 0;
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
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void cargarUnidades() {
        try {
            unidades = Unidad.obtenerUnidades();
            if (unidades != null) {
                cbUnidad.setItems(unidades);
                cbUnidad.getSelectionModel().selectFirst();                
            }
            
            /*categorias = Categoria.obtenerListaCategorias();
            if (categorias != null) {
                cbUnidad.setItems(categorias);
                cbUnidad.getSelectionModel().selectFirst();
            }*/
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
                    tfCosto.setText(String.valueOf(c.getCosto()));
                    cbUnidad.setValue(Unidad.obtenerUnidad(c.getUnidad()));
                    idArticuloEnlazado = c.getIdArticuloEnlazado();
                    cantidadEnlazado = c.getCantidadEnlazado();
                    btEnlazado.setText(Articulo.obtenerArticuloId(idArticuloEnlazado).getNombre());
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
            deshabilitar(false);
            btEnlazado.setText("-");
            tipo = "NUEVO";
            idArticuloEnlazado = 0;
            cantidadEnlazado = 0;
        } else {
            if ("NUEVO".equals(tipo)) {
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
                    Unidad u = cbUnidad.getValue();
                    String unidad = u.getNombre();
                    Float costo = Float.parseFloat(tfCosto.getText());

                    Articulo.nuevoArticulo(nombre, codigo, existencia, idCategoria, precio, "NUEVO", unidad, costo, idArticuloEnlazado, cantidadEnlazado);

                    btNuevo.setText("Nuevo");
                    btActualizar.setText("Actualizar");
                    tfNombre.setText("");
                    tfCodigo.setText("");
                    tfExistencia.setText("");
                    tfPrecio.setText("");

                    deshabilitar(true);

                    cargarArticulos();
                } catch (NumberFormatException exc) {

                }
            } else if ("ACTUALIZAR".equals(tipo)) {
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
                        Unidad u = cbUnidad.getValue();
                        String unidad = u.getNombre();
                        Float costo = Float.parseFloat(tfCosto.getText());

                        Articulo.actualizarArticulo(idArticulo, nombre, codigo, existencia, idCategoria, precio, existencia - articuloActual.getExistencia(), "ACTUALIZACION", unidad, costo, idArticuloEnlazado, cantidadEnlazado);

                        btNuevo.setText("Nuevo");
                        btActualizar.setText("Actualizar");
                        tfNombre.setText("");
                        tfCodigo.setText("");
                        tfExistencia.setText("");
                        tfPrecio.setText("");
                        deshabilitar(true);
                        tipo = "";

                        cargarArticulos();
                    } catch (NumberFormatException exc) {

                    }
                }
            }
        }
    }
    
    public void deshabilitar(boolean state) {
        tfNombre.setDisable(state);
        tfCodigo.setDisable(state);
        tfExistencia.setDisable(state);
        tfPrecio.setDisable(state);
        tfCosto.setDisable(state);
        btEnlazado.setDisable(state);
    }
    
    public void actualizarArticulo() {
        if (btActualizar.getText().contains("Actualizar")) {
            btNuevo.setText("Guardar");
            btActualizar.setText("Cancelar");
            deshabilitar(false);
            tipo = "ACTUALIZAR";
        } else {
            btNuevo.setText("Nuevo");
            btActualizar.setText("Actualizar");
            tfNombre.setText("");
            tfCodigo.setText("");
            tfExistencia.setText("");
            tfPrecio.setText("");
            deshabilitar(true);
            tipo = "";
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
    
    public void mostrarEnlazado() {
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Enlazado.fxml"));
            Parent root = (Parent)loader.load();
            EnlazadoController controller = loader.<EnlazadoController>getController();
            controller.setEnlace(idArticuloEnlazado, cantidadEnlazado);
            controller.setParent(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            
        }
    }
    
    public void setEnlace(int id, int c) {
        idArticuloEnlazado = id;
        cantidadEnlazado = c;
        btEnlazado.setText(Articulo.obtenerArticuloId(idArticuloEnlazado).getNombre());
    }
}
