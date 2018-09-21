/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Abono;
import Data.Articulo;
import Data.Compra;
import Data.Proveedor;
import Data.Turno;
import Data.Venta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class CorteComprasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Compra> tvCompras;
    @FXML private TableColumn<Compra, String> tcNumero;
    @FXML private TableColumn<Compra, String> tcComprador;
    @FXML private TableColumn<Compra, String> tcProveedor;
    @FXML private TableColumn<Compra, String> tcFecha;
    @FXML private TableColumn<Compra, String> tcTotal;
    @FXML private TableColumn<Compra, String> tcNotas;
    @FXML private TableColumn<Compra, String> tcNumeroNota;
    @FXML private TableColumn<Compra, String> tcUsarCaja;
    
    @FXML private TableView<Articulo> tvDetalleCompra;
    @FXML private TableColumn<Articulo, String> tcNombre;
    @FXML private TableColumn<Articulo, String> tcCantidad;
    
    @FXML private ComboBox cbTurnos;
    @FXML private ComboBox cbProveedores;
    
    @FXML private TextField tfFechaInicio;
    
    private ObservableList<Turno> turnos;
    private ObservableList<Proveedor> proveedores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        obtenerTurnos();
        cargarProveedores();
    }
    
    public void obtenerTurnos() {
        turnos = Turno.obtenerTurnos();
        cbTurnos.setItems(turnos);
        cbTurnos.getSelectionModel().selectFirst();
    }
    
    public void cargarProveedores() {
        try {
            proveedores = Proveedor.obtenerListaProveedores();
            if (proveedores != null) {
                cbProveedores.setItems(proveedores);
                cbProveedores.getSelectionModel().selectFirst();
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void tfFechaInicio_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                /*
                tvVentas.getItems().clear();
                ventas = Venta.obtenerVentasFecha(tfFechaInicio.getText(), (Turno)cbTurnos.getValue());
                tvVentas.setItems(ventas);
                tvAbonos.getItems().clear();
                abonos = Abono.obtenerAbonosFecha(tfFechaInicio.getText());
                tvAbonos.setItems(abonos);
                calcularCorte();*/
            } catch (Exception exc) {
                
            }
        }
    }
    
}
