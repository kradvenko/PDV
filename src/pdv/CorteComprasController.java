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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    @FXML private TableColumn<Articulo, String> tcCosto;
    
    @FXML private ComboBox cbTurnos;
    @FXML private ComboBox cbProveedores;
    
    @FXML private TextField tfFechaInicio;
    
    @FXML private Label lblCompraTotal;
    @FXML private Label lblCajaCompras;
    @FXML private Label lblCajaVentas;
    
    private ObservableList<Compra> compras;
    private ObservableList<Turno> turnos;
    private ObservableList<Proveedor> proveedores;
    private ObservableList<Articulo> detalle;
    
    private Compra compraActual;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNumero.setCellValueFactory(new PropertyValueFactory<Compra, String>("idCompra"));
        tcComprador.setCellValueFactory(new PropertyValueFactory<Compra, String>("usuarioCompra"));
        tcProveedor.setCellValueFactory(new PropertyValueFactory<Compra, String>("proveedor"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<Compra, String>("fechaCompra"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<Compra, String>("total"));
        tcNotas.setCellValueFactory(new PropertyValueFactory<Compra, String>("notas"));
        tcNumeroNota.setCellValueFactory(new PropertyValueFactory<Compra, String>("numeroNota"));
        tcUsarCaja.setCellValueFactory(new PropertyValueFactory<Compra, String>("usarCaja"));
        
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadCompra"));
        tcCosto.setCellValueFactory(new PropertyValueFactory<Articulo, String>("costoCompra"));
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();            
        String fecha = dateFormat.format(cal.getTime());
        tfFechaInicio.setText(fecha.substring(0, 10));
        
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
                tvCompras.getItems().clear();
                compras = Compra.obtenerCompras(tfFechaInicio.getText(), (Turno)cbTurnos.getValue(), "%");
                tvCompras.setItems(compras);
                calcularCorte();
            } catch (Exception exc) {
                
            }
        }
    }
    
    public void tvCompras_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Compra c = (Compra) v.getSelectionModel().getSelectedItem();
                    //tvVentaDetalle.getItems().clear();
                    compraActual = c;
                    detalle = compraActual.getDetalle();
                    tvDetalleCompra.setItems(detalle);
                }
            } else {
                
            }
        } catch (Exception e) {
            
        }
    }
    
    public void calcularCorte() {
        Float total = 0f;
        for (int i = 0; i < tvCompras.getItems().size(); i++) {
            total = total + tvCompras.getItems().get(i).getTotal();
        }        
        lblCompraTotal.setText("$ " + String.valueOf(total));
        lblCajaCompras.setText("$ " + String.valueOf(Compra.obtenerTotalCompras("CAJA COMPRAS", tfFechaInicio.getText(), (Turno)cbTurnos.getValue())));
        lblCajaVentas.setText("$ " + String.valueOf(Compra.obtenerTotalCompras("CAJA VENTAS", tfFechaInicio.getText(), (Turno)cbTurnos.getValue())));
    }
    
    public void comprasProveedor() {
        tvCompras.getItems().clear();
        compras = Compra.obtenerComprasProveedor(String.valueOf(((Proveedor)cbProveedores.getValue()).getIdProveedor()));
        tvCompras.setItems(compras);
        calcularCorte();
        lblCajaCompras.setText("$ -");
        lblCajaVentas.setText("$ -");
    }
    
}
