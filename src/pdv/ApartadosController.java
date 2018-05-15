/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Abono;
import Data.Apartado;
import Data.Articulo;
import Data.Reporte;
import Data.Usuario;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * @author carloscui
 */
public class ApartadosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField tfFecha;
    @FXML private TextField tfNombre;
    @FXML private TextField tfAbono;
    
    @FXML private TableView<Apartado> tvApartados;
    @FXML private TableColumn tcApartadoFecha;
    @FXML private TableColumn tcApartadoCliente;
    @FXML private TableColumn tcApartadoTotal;
    @FXML private TableColumn tcApartadoResta;
    
    @FXML private TableView<Abono> tvAbonos;
    @FXML private TableColumn tcAbonoFecha;
    @FXML private TableColumn tcAbonoAbono;
    
    private ObservableList<Apartado> apartados;
    private ObservableList<Abono> abonos;
    
    private Apartado apartadoActual;
    private Float totalApartadoAbonos;
    private String fechaActual;    
    
    private Usuario usuarioActual;
    private Abono abonoActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcApartadoFecha.setCellValueFactory(new PropertyValueFactory<Apartado, String>("fecha"));
        tcApartadoCliente.setCellValueFactory(new PropertyValueFactory<Apartado, String>("cliente"));        
        tcApartadoTotal.setCellValueFactory(new PropertyValueFactory<Apartado, String>("totalVenta"));
        tcApartadoResta.setCellValueFactory(new PropertyValueFactory<Apartado, String>("cambio"));
        
        tcAbonoFecha.setCellValueFactory(new PropertyValueFactory<Abono, String>("fecha"));
        tcAbonoAbono.setCellValueFactory(new PropertyValueFactory<Abono, String>("abono"));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String fecha = dateFormat.format(cal.getTime());
        tfFecha.setText(fecha.substring(0, 10));
        fechaActual = fecha;
        apartados = Apartado.obtenerApartadosFecha(tfFecha.getText());        
        tvApartados.getItems().clear();
        tvApartados.setItems(apartados);
    }
    
    public void setUsuario(Usuario u) {
        this.usuarioActual = u;
    }
    
    public void tfFecha_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                tvApartados.getItems().clear();
                apartados = Apartado.obtenerApartadosFecha(tfFecha.getText());
                tvApartados.setItems(apartados);
                tvAbonos.getItems().clear();
                //abonos = Abono.obtenerAbonosFecha(tfFechaInicio.getText());
                tvAbonos.setItems(abonos);
                //calcularCorte();
            } catch (Exception exc) {
                
            }
        }
    }
    
    public void tvVentas_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Apartado c = (Apartado) v.getSelectionModel().getSelectedItem();                    
                    apartadoActual = c;
                    //tvAbonos.getItems().clear();
                    abonos = Abono.obtenerAbonosApartado(c.getIdApartado());
                    tvAbonos.setItems(abonos);
                    calcularTotalAbonos();
                }
            } else {
                
            }
        } catch (Exception e) {
            
        }
    }
    
    public void abonar_efectivo() {
        try {
            Float abono = Float.parseFloat(tfAbono.getText());
            if (abono > apartadoActual.getCambio()) {
                abono = 0f;
                tfAbono.setText("0");
            } else {
                Apartado.abonarApartado(apartadoActual.getIdApartado(), fechaActual, abono, "EFECTIVO");
                Apartado.actualizarApartadoResta(apartadoActual.getIdApartado(), apartadoActual.getCambio() - abono);
                tfAbono.setText("0");                
                cargarApartados();
                abonos.clear();
                tvAbonos.getItems().clear();
            }
        } catch (NumberFormatException exc) {
            tfAbono.setText("0");
        }
    }
    
    public void abonar_tarjeta() {
        try {
            Float abono = Float.parseFloat(tfAbono.getText());
            if (abono > apartadoActual.getCambio()) {
                abono = 0f;
                tfAbono.setText("0");
            } else {
                Apartado.abonarApartado(apartadoActual.getIdApartado(), fechaActual, abono, "TARJETA");
                Apartado.actualizarApartadoResta(apartadoActual.getIdApartado(), apartadoActual.getCambio() - abono);
                tfAbono.setText("0");                
                cargarApartados();
                abonos.clear();
                tvAbonos.getItems().clear();
            }
        } catch (NumberFormatException exc) {
            tfAbono.setText("0");
        }
    }
    
    public void calcularTotalAbonos() {
        try {
            totalApartadoAbonos = 0f;
            for (int i = 0; i < abonos.size(); i++) {
                Abono a = abonos.get(i);
                totalApartadoAbonos = totalApartadoAbonos + a.getAbono();
            }
        } catch (Exception exc) {
            
        }
    }
    
    public void cargarApartados() {
        apartados = Apartado.obtenerApartadosFecha(tfFecha.getText());        
        tvApartados.getItems().clear();
        tvApartados.setItems(apartados);
    }
    
    public void cargarAbonos() {
        tvAbonos.getItems().clear();
        abonos = Abono.obtenerAbonosApartado(apartadoActual.getIdApartado());
        tvAbonos.setItems(abonos);
    }
    
    public void tvAbonos_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Abono a = (Abono) v.getSelectionModel().getSelectedItem();
                    //tvVentaDetalle.getItems().clear();
                    abonoActual = a;
                }
            } else {
                
            }
        } catch (Exception e) {
            
        }
    }
    
    public void borrarAbono() {
        if (usuarioActual.getTipo().contains("USUARIO")) {
            return;
        }
        if (abonoActual != null) {
            Apartado.cancelarAbono(abonoActual);
            cargarAbonos();
            calcularTotalAbonos();
            Apartado.actualizarApartadoResta(apartadoActual.getIdApartado(), apartadoActual.getTotalVenta() - totalApartadoAbonos);
            cargarApartados();
            abonoActual = null;
            tvAbonos.getItems().clear();
        }
    }
    
    public void imprimirApartado() {
        Reporte r = new Reporte();
        Apartado a = Apartado.obtenerApartadoId(apartadoActual.getIdApartado());
        r.setApartadoActual(a);
        r.crearApartadoImprimir();
        r.crearApartadoAbonosImprimir();
    }
}
