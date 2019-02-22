/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Abono;
import Data.Articulo;
import Data.Compra;
import Data.Reporte;
import Data.Turno;
import Data.Usuario;
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
public class CorteGanaciasController implements Initializable {

    @FXML private TableView<Venta> tvVentas;
    @FXML private TableColumn<Venta, String> tcVentaNumero;
    @FXML private TableColumn<Venta, String> tcVentaFecha;
    @FXML private TableColumn<Venta, String> tcVentaTipo;
    @FXML private TableColumn<Venta, String> tcVentaSubtotal;
    @FXML private TableColumn<Venta, String> tcVentaDescuentoEfectivo;
    @FXML private TableColumn<Venta, String> tcVentaDescuentoPorcentaje;
    @FXML private TableColumn<Venta, String> tcVentaTotal;
    @FXML private TableColumn<Venta, String> tcEstado;
    @FXML private TableColumn<Venta, String> tcVendedor;
    @FXML private TableColumn<Venta, String> tcGanancia;
    
    @FXML private TableView<Articulo> tvVentaDetalle;
    @FXML private TableColumn<Articulo, String> tcDetalleNombre;
    @FXML private TableColumn<Articulo, String> tcDetalleCodigo;
    @FXML private TableColumn<Articulo, String> tcDetallePrecio;
    @FXML private TableColumn<Articulo, String> tcDetalleCantidad;
    @FXML private TableColumn<Articulo, String> tcDetalleImporte;
    @FXML private TableColumn<Articulo, String> tcDetalleGanancia;
    
    @FXML private TableView<Abono> tvAbonos;
    @FXML private TableColumn<Abono, String> tcAbonoFecha;
    @FXML private TableColumn<Abono, String> tcAbonoAbono;
    @FXML private TableColumn<Abono, String> tcAbonoTipo;
    
    @FXML private TextField tfFechaInicio;
    
    @FXML private Label lblVentaEfectivo;
    @FXML private Label lblVentaTarjeta;
    @FXML private Label lblVentaTotal;
    @FXML private Label lblAbonoEfectivo;
    @FXML private Label lblAbonoTarjeta;
    @FXML private Label lblCajaVentas;
    
    @FXML private ComboBox cbTurnos;
    
    private ObservableList<Venta> ventas;
    private ObservableList<Articulo> detalle;
    private ObservableList<Abono> abonos;
    private ObservableList<Turno> turnos;
    
    private Venta ventaActual;
    private Usuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDetalleNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcDetalleCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));        
        tcDetallePrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        tcDetalleCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadVenta"));
        tcDetalleImporte.setCellValueFactory(new PropertyValueFactory<Articulo, String>("totalVenta"));
        tcDetalleGanancia.setCellValueFactory(new PropertyValueFactory<Articulo, String>("gananciaEnVenta"));
        
        tcVentaNumero.setCellValueFactory(new PropertyValueFactory<Venta, String>("idVenta"));
        tcVentaFecha.setCellValueFactory(new PropertyValueFactory<Venta, String>("fecha"));
        tcVentaTipo.setCellValueFactory(new PropertyValueFactory<Venta, String>("tipo"));
        tcVentaSubtotal.setCellValueFactory(new PropertyValueFactory<Venta, String>("subTotalVenta"));
        tcVentaDescuentoEfectivo.setCellValueFactory(new PropertyValueFactory<Venta, String>("descuentoEfectivo"));
        tcVentaDescuentoPorcentaje.setCellValueFactory(new PropertyValueFactory<Venta, String>("descuentoPorcentaje"));
        tcVentaTotal.setCellValueFactory(new PropertyValueFactory<Venta, String>("totalVenta"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<Venta, String>("estado"));
        tcVendedor.setCellValueFactory(new PropertyValueFactory<Venta, String>("vendedor"));
        tcGanancia.setCellValueFactory(new PropertyValueFactory<Venta, String>("ganancia"));
        
        tcAbonoFecha.setCellValueFactory(new PropertyValueFactory<Abono, String>("fecha"));
        tcAbonoAbono.setCellValueFactory(new PropertyValueFactory<Abono, String>("abono"));
        tcAbonoTipo.setCellValueFactory(new PropertyValueFactory<Abono, String>("tipo"));
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();            
        String fecha = dateFormat.format(cal.getTime());
        tfFechaInicio.setText(fecha.substring(0, 10));
        tvVentas.getItems().clear();
        //ventas = Venta.obtenerVentasFecha(tfFechaInicio.getText());
        //tvVentas.setItems(ventas);
        tvAbonos.getItems().clear();
        //abonos = Abono.obtenerAbonosFecha(tfFechaInicio.getText());
        //tvAbonos.setItems(abonos);
        //calcularCorte();
        obtenerTurnos();
    }
    
    public void setUsuario(Usuario u) {
        this.usuarioActual = u;
    }
    
    public void tfFechaInicio_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                tvVentas.getItems().clear();
                ventas = Venta.obtenerVentasFecha(tfFechaInicio.getText(), (Turno)cbTurnos.getValue());
                tvVentas.setItems(ventas);
                tvAbonos.getItems().clear();
                abonos = Abono.obtenerAbonosFecha(tfFechaInicio.getText());
                tvAbonos.setItems(abonos);
                calcularCorte();
            } catch (Exception exc) {
                
            }
        }
    }
    
    public void tvVentas_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Venta c = (Venta) v.getSelectionModel().getSelectedItem();
                    //tvVentaDetalle.getItems().clear();
                    ventaActual = c;
                    detalle = ventaActual.getDetalle();
                    tvVentaDetalle.setItems(detalle);
                }
            } else {
                
            }
        } catch (Exception e) {
            
        }
    }
    
    public void imprimir() {        
        if (ventaActual != null) {
            Reporte r = new Reporte();
            r.setVentaActual(ventaActual);
            r.crearTicket();
        }
    }
    
    public void calcularCorte() {
        Float totalEfectivo = 0f;
        Float totalTarjeta = 0f;
        Float abonoEfectivo = 0f;
        Float abonoTarjeta = 0f;
        Float totalVenta = 0f;
        
        if (ventas.size() > 0) {
            for (int i = 0; i < ventas.size(); i++) {
                Venta v = ventas.get(i);
                if (v.getEstado().contains("CANCELADA")) {
                    continue;
                }
                if (v.getTipo().contains("EFECTIVO")) {
                    totalEfectivo = totalEfectivo + v.getTotalVenta();
                } else if (v.getTipo().contains("TARJETA")) {
                    totalTarjeta = totalTarjeta + v.getTotalVenta();
                }
            }
        }
        
        if (abonos.size() > 0) {
            for (int i = 0; i < abonos.size(); i++) {
                Abono v = abonos.get(i);                
                if (v.getTipo().contains("EFECTIVO")) {
                    abonoEfectivo = abonoEfectivo + v.getAbono();
                } else if (v.getTipo().contains("TARJETA")) {
                    abonoTarjeta = abonoTarjeta + v.getAbono();
                }
            }
        }
        
        totalVenta = totalEfectivo + totalTarjeta + abonoEfectivo + abonoTarjeta;
        lblVentaEfectivo.setText("$ " + String.format("%.2f", totalEfectivo));
        lblVentaTarjeta.setText("$ " + String.format("%.2f", totalTarjeta));
        lblAbonoEfectivo.setText("$ " + String.format("%.2f", abonoEfectivo));
        lblAbonoTarjeta.setText("$ " + String.format("%.2f", abonoTarjeta));
        lblVentaTotal.setText("$ " + String.format("%.2f", totalVenta));
        
        lblCajaVentas.setText("$ " + String.valueOf(Compra.obtenerTotalCompras("CAJA VENTAS", tfFechaInicio.getText(), (Turno)cbTurnos.getValue())));
    }
    
    public void imprimirCorte() {
        Reporte r = new Reporte();
        r.setCorte(ventas);
        r.crearCorte();
        r.setAbonos(abonos);
        r.crearApartadoAbonosCorteImprimir();
    }
    
    public void cancelarVenta() {
        if (usuarioActual.getTipo().contains("USUARIO")) {
            return;
        }
        if (ventaActual != null) {
            if (ventaActual.getEstado().contains("ACTIVA")) {
                Venta.cancelarVenta(ventaActual, "CANCELADA");
                tvVentas.getItems().clear();
                ventas = Venta.obtenerVentasFecha(tfFechaInicio.getText());
                tvVentas.setItems(ventas);
                tvVentas.getColumns().get(0).setVisible(false);
                tvVentas.getColumns().get(0).setVisible(true);
                calcularCorte();
            }
        }
    }
    
    public void activarVenta() {
        if (usuarioActual.getTipo().contains("USUARIO")) {
            return;
        }
        if (ventaActual != null) {
            if (ventaActual.getEstado().contains("CANCELADA")) {
                Venta.cancelarVenta(ventaActual, "ACTIVA");
                tvVentas.getItems().clear();
                ventas = Venta.obtenerVentasFecha(tfFechaInicio.getText());
                tvVentas.setItems(ventas);
                tvVentas.getColumns().get(0).setVisible(false);
                tvVentas.getColumns().get(0).setVisible(true);
                calcularCorte();
            }
        }
    }
    
    public void enviarVentas() {
        
    }
    
    public void obtenerTurnos() {
        turnos = Turno.obtenerTurnos();
        cbTurnos.setItems(turnos);
        cbTurnos.getSelectionModel().selectFirst();
    }
    
    
}
