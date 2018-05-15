/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Abono;
import Data.Articulo;
import Data.Venta;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author carloscui
 */
public class BitacoraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<Articulo> tvEntregas;
    @FXML private TableColumn<Articulo, String> tcArticulo;
    @FXML private TableColumn<Articulo, String> tcCantidad;
    @FXML private TableColumn<Articulo, String> tcPrecio;
    @FXML private TableColumn<Articulo, String> tcFolio;
    
    @FXML TextField tfFecha;
    @FXML TextField tfFolioEntrega;
    @FXML Label lblTotal;
    
    private ObservableList<Articulo> entregas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        tcArticulo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadEntrega"));        
        tcPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        tcFolio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("folio"));
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();            
        String fecha = dateFormat.format(cal.getTime());
        tfFecha.setText(fecha.substring(0, 10));        
        entregas = Articulo.obtenerArticulosEntregaFecha(tfFecha.getText());
        tvEntregas.setItems(entregas); 
        calcularTotal();
    }    
    
    public void tfFecha_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                tvEntregas.getItems().clear();
                entregas = Articulo.obtenerArticulosEntregaFecha(tfFecha.getText());
                tvEntregas.setItems(entregas);
                calcularTotal();
            } catch (Exception exc) {
                
            }
        }
    }
    
    public void tfFolio_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                tvEntregas.getItems().clear();
                entregas = Articulo.obtenerArticulosEntregaFolio(tfFolioEntrega.getText());
                tvEntregas.setItems(entregas);
                calcularTotal();
            } catch (Exception exc) {
                
            }
        }
    }
    
    public void calcularTotal() {
        float total = 0;
        for (int i = 0; i < entregas.size(); i++) {
            Articulo a = new Articulo();
            a = entregas.get(i);
            total = total + (a.getCantidadEntrega() * a.getPrecio());            
        }
        lblTotal.setText(String.valueOf(total));
    }
    
}
