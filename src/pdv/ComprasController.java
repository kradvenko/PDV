/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Articulo;
import Data.Categoria;
import Data.Compra;
import Data.Proveedor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * @author kradv
 */
public class ComprasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Articulo> tvArticulos;
    @FXML TableColumn<Articulo, String> tcNombre;
    
    @FXML TableView<Articulo> tvListaCompra;
    @FXML TableColumn<Articulo, String> tcNombreCompra;
    @FXML TableColumn<Articulo, String> tcCantidadCompra;
    @FXML TableColumn<Articulo, String> tcCostoCompra;
    
    @FXML ComboBox<Categoria> cbCategoria;
    @FXML ComboBox<Proveedor> cbProveedor;
    
    @FXML TextField tfFiltro;
    
    @FXML Button btnCerrar;
    
    ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    ObservableList<Articulo> listaCompra = FXCollections.observableArrayList();
    ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    
    Categoria categoriaActual;
    Articulo articuloActual;
    Articulo articuloCompraActual;
    Proveedor proveedorActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        
        tcNombreCompra.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCantidadCompra.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadVenta"));
        tcCostoCompra.setCellValueFactory(new PropertyValueFactory<Articulo, String>("costo"));
        
        cargarCategorias();
        cargarArticulos();
        cargarProveedores();
    }
    
    public void tfFiltro_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            if (tfFiltro.getText().length() > 0) {
                articulos = Articulo.obtenerArticulosNombreCategoria(categoriaActual.getNombre(), "%" + tfFiltro.getText() + "%");
            } else {
                articulos = Articulo.obtenerArticulosNombreCategoria(categoriaActual.getNombre(), "%");
            }
            tvArticulos.setItems(articulos);
            tfFiltro.setText("");
        }
    }
    
    public void tvArticulos_Click(MouseEvent event) {
        TableView v = (TableView) event.getSource();
        Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
        articuloActual = c;
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    agregarACompra();
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void cargarCategorias() {
        try {
            categorias = Categoria.obtenerListaCategorias();
            if (categorias != null) {
                cbCategoria.setItems(categorias);
                cbCategoria.getSelectionModel().selectFirst();
                categoriaActual = cbCategoria.getValue();
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void cargarArticulos() {
        try {
            categoriaActual = cbCategoria.getValue();
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosNombreCategoria(categoriaActual.getNombre(), "%");
            tvArticulos.setItems(articulos);            
        } catch (Exception exc) {
            
        }
    }
    
    public void agregarACompra() {
        if (articuloActual != null) {
            articuloActual.setCantidadVenta(0f);
            listaCompra.add(articuloActual);
            tvListaCompra.setItems(listaCompra);
            articuloCompraActual = articuloActual;
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CantidadCompra.fxml"));
                Parent root = (Parent)loader.load();
                CantidadCompraController controller = loader.<CantidadCompraController>getController();
                controller.parent = this;
                controller.setCantidad(articuloCompraActual.getCantidadVenta(), articuloCompraActual.getCosto());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (Exception e) {

            }
        }
    }
    
    public void tvListaCompra_Click(MouseEvent event) {
        TableView v = (TableView) event.getSource();
        Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
        articuloCompraActual = c;
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CantidadCompra.fxml"));
                        Parent root = (Parent)loader.load();
                        CantidadCompraController controller = loader.<CantidadCompraController>getController();
                        controller.parent = this;
                        controller.setCantidad(articuloCompraActual.getCantidadVenta(), articuloCompraActual.getCosto());
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.showAndWait();
                    } catch (Exception e) {

                    }
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void ActualizarCantidadArticuloCompra(float cantidad, float costo) {
        listaCompra.get(listaCompra.indexOf(articuloCompraActual)).setCantidadVenta(cantidad);
        listaCompra.get(listaCompra.indexOf(articuloCompraActual)).setCosto(costo);
        tvListaCompra.refresh();
    }
    
    public void mostrarProveedorDialog() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarProveedor.fxml"));
            Parent root = (Parent)loader.load();
            AgregarProveedorController controller = loader.<AgregarProveedorController>getController();            
            controller.parent = this;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {

        }
    }
    
    public void cargarProveedores() {
        try {
            proveedores = Proveedor.obtenerListaProveedores();
            if (proveedores != null) {
                cbProveedor.setItems(proveedores);
                cbProveedor.getSelectionModel().selectFirst();
                proveedorActual = cbProveedor.getValue();
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void cbProveedor_IndexChange() {
        proveedorActual = cbProveedor.getValue();
    }
    
    public void guardarCompra() {
        try {
            if (listaCompra.size() > 0) {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogGuardarCompra.fxml"));
                Parent root = (Parent)loader.load();
                DialogGuardarCompraController controller = loader.<DialogGuardarCompraController>getController();            
                controller.parent = this;
                controller.setTotal(calcularTotalCompra());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.showAndWait();
            } else {
                mostrarMensaje("No hay artículos en la lista de compra.");
            }
        } catch (Exception exc) {
            mostrarMensaje(exc.getMessage());
        }
    }
    
    public void guardarCompraDialog(String total, String notas, String numeroNota, int idUsuarioCompra, String usarCaja, float cantidadCajaVenta) {
        Compra c = new Compra();
        c.guardarCompra(proveedorActual.getIdProveedor(), listaCompra, Float.parseFloat(total), notas, numeroNota, idUsuarioCompra, usarCaja, cantidadCajaVenta);
        mostrarMensaje("Se ha ingresado la compra.");
        cerrar();
    }
    
    public float calcularTotalCompra() {
        float total = 0;
        for (int i = 0; i < listaCompra.size(); i++) {
            total = total + (listaCompra.get(i).getCantidadVenta() * listaCompra.get(i).getCosto());
        }
        return total;
    }
    
    public void mostrarMensaje(String mensaje) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogWindow.fxml"));
            Parent root = (Parent)loader.load();
            DialogWindowController controller = loader.<DialogWindowController>getController();
            controller.setMessage(mensaje);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {

        }
    }
}
