/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Apartado;
import Data.Articulo;
import Data.Categoria;
import Data.Reporte;
import Data.Venta;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class NuevaVentaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Articulo> tvArticulos;
    @FXML TableColumn<Articulo, String> tcNombre;
    @FXML TableColumn<Articulo, String> tcCodigo;    
    @FXML TableColumn<Articulo, String> tcPrecio;
    
    @FXML TableView<Articulo> tvVenta;
    @FXML TableColumn<Articulo, String> tcVentaNombre;
    @FXML TableColumn<Articulo, String> tcVentaCodigo;
    @FXML TableColumn<Articulo, String> tcVentaPrecio;
    @FXML TableColumn<Articulo, String> tcVentaCantidad;
    @FXML TableColumn<Articulo, String> tcVentaImporte;
    @FXML TableColumn<Articulo, String> tcVentaDescuentoArticuloEfectivo;
    @FXML TableColumn<Articulo, String> tcVentaDescuentoArticuloPorcentaje;
    
    @FXML TextField tfBusquedaCodigo;
    @FXML TextField tfBusquedaNombre;
    
    @FXML TextField tfDescuentoEfectivo;
    @FXML TextField tfDescuentoPorcentaje;
    
    @FXML TextField tfVendedor;
    
    @FXML Label lblSubTotalVenta;
    @FXML Label lblTotalVenta;
    
    @FXML CheckBox cbImprimir;
    
    @FXML TabPane tpMain;
    
    private ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    private ObservableList<Articulo> detalleVenta = FXCollections.observableArrayList();
    
    private Articulo articuloActual;
    private Articulo articuloVentaActual;
    
    private Float subTotalVenta;
    private Float descuentoEfectivo;
    private Float descuentoPorcentaje;
    private Float totalVenta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));        
        tcPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        
        tcVentaNombre.setCellValueFactory(new PropertyValueFactory<Articulo, String>("nombre"));
        tcVentaCodigo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigo"));        
        tcVentaPrecio.setCellValueFactory(new PropertyValueFactory<Articulo, String>("precio"));
        tcVentaCantidad.setCellValueFactory(new PropertyValueFactory<Articulo, String>("cantidadVenta"));
        tcVentaImporte.setCellValueFactory(new PropertyValueFactory<Articulo, String>("totalVenta"));
        tcVentaDescuentoArticuloEfectivo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descuentoArticuloEfectivo"));
        tcVentaDescuentoArticuloPorcentaje.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descuentoArticuloPorcentaje"));
        
        subTotalVenta = 0f;
        totalVenta = 0f;        
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfBusquedaCodigo.requestFocus();
            }
        });
    }
    
    public void tfBusquedaCodigo_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosCodigo(tfBusquedaCodigo.getText());
            tvArticulos.setItems(articulos);
            if (1 == articulos.size()) {
                articuloActual = articulos.get(0);
                agregarArticuloVenta(articuloActual);
            }
        }
    }
    
    public void tfBusquedaNombre_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tvArticulos.getItems().clear();
            articulos = Articulo.obtenerArticulosNombre(tfBusquedaNombre.getText());
            tvArticulos.setItems(articulos);            
        }
    }
    
    public void tvArticulos_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    TableView v = (TableView) event.getSource();
                    Articulo c = new Articulo((Articulo) v.getSelectionModel().getSelectedItem());
                    articuloActual = c;
                    agregarArticuloVenta(c);
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void tvVenta_Click(MouseEvent event) {
        try {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    TableView v = (TableView) event.getSource();
                    Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloVentaActual = c;
                    int index = v.getSelectionModel().getSelectedIndex();
                    
                    Stage stage = new Stage();        
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogNuevaCantidadVenta.fxml"));
                    Parent root = (Parent)loader.load();
                    DialogNuevaCantidadVentaController controller = loader.<DialogNuevaCantidadVentaController>getController();
                    controller.setParent(this);
                    controller.setCantidad(articuloVentaActual.getCantidadVenta(), articuloVentaActual.getDescuentoArticuloEfectivo(), articuloVentaActual.getDescuentoArticuloPorcentaje(), index);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.showAndWait();
                } else if(event.getClickCount() == 1){
                    TableView v = (TableView) event.getSource();
                    Articulo c = (Articulo) v.getSelectionModel().getSelectedItem();
                    articuloVentaActual = c;
                }
            } else {
            }
        } catch (Exception e) {
            
        }
    }
    
    public void agregarArticuloVenta(Articulo a) {
        try {
            /*
            Esta parte del código verifica si el artículo que se está ingresando a la venta ya existe en la misma
            y agrega uno más a la cantidad del artículo en la venta.
            Esto ocasiona que no se pueda vender el artículo dos o más veces en la venta actual con diferentes precios.            
            for (int i = 0; i < detalleVenta.size(); i++) {
                Articulo x = detalleVenta.get(i);
                if (x.getIdArticulo() == a.getIdArticulo()) {
                    detalleVenta.get(i).setCantidadVenta(detalleVenta.get(i).getCantidadVenta() + 1);
                    a.setTotalVenta(articuloVentaActual.getCantidadVenta() * articuloVentaActual.getPrecio() * (1 - articuloVentaActual.getDescuentoArticuloPorcentaje() / 100) - articuloVentaActual.getDescuentoArticuloEfectivo());
                    tvVenta.getColumns().get(0).setVisible(false);
                    tvVenta.getColumns().get(0).setVisible(true);
                    tvVenta.setItems(detalleVenta);
                    calcularTotalVenta();
                    return;
                }
            }*/
            a.setCantidadVenta(1);
            a.setTotalVenta(1f * a.getPrecio());
            a.setDescuentoArticuloEfectivo(0f);
            a.setDescuentoArticuloPorcentaje(0f);
            int random = 1 + (int)(Math.random() * ((500 - 1) + 1));
            a.setRandomStuff(random);            
            detalleVenta.add(a);
            tvVenta.setItems(detalleVenta);
            tvVenta.getColumns().get(0).setVisible(false);
            tvVenta.getColumns().get(0).setVisible(true);
            calcularTotalVenta();
        } catch (Exception exc) {
            
        }
    }
    
    public void setCantidadArticuloVenta(int cantidad, float descuentoEfectivo, float descuentoPorcentaje, int index) {
        articuloVentaActual.setCantidadVenta(cantidad);
        articuloVentaActual.setDescuentoArticuloEfectivo(descuentoEfectivo);
        articuloVentaActual.setDescuentoArticuloPorcentaje(descuentoPorcentaje);
        articuloVentaActual.setTotalVenta(articuloVentaActual.getCantidadVenta() * articuloVentaActual.getPrecio() * (1 - articuloVentaActual.getDescuentoArticuloPorcentaje() / 100) - articuloVentaActual.getDescuentoArticuloEfectivo());
        /*detalleVenta.get(index).setCantidadVenta(cantidad);
        detalleVenta.get(index).setDescuentoArticuloEfectivo(descuentoEfectivo);
        detalleVenta.get(index).setDescuentoArticuloPorcentaje(descuentoPorcentaje);
        detalleVenta.get(index).setTotalVenta(articuloVentaActual.getCantidadVenta() * articuloVentaActual.getPrecio() * (1 - articuloVentaActual.getDescuentoArticuloPorcentaje() / 100) - articuloVentaActual.getDescuentoArticuloEfectivo());*/
        tvVenta.getColumns().get(0).setVisible(false);
        tvVenta.getColumns().get(0).setVisible(true);
        calcularTotalVenta();
    }
    
    public void eliminarArticuloVenta() {
        if (articuloVentaActual != null) {
            detalleVenta.remove(articuloVentaActual);
            tvVenta.getColumns().get(0).setVisible(false);
            tvVenta.getColumns().get(0).setVisible(true);
            calcularTotalVenta();
        }
    }
    
    public void calcularTotalVenta() {
        subTotalVenta = 0f;
        for (int i = 0; i < detalleVenta.size(); i++) {
            subTotalVenta = subTotalVenta + detalleVenta.get(i).getTotalVenta();
        }
        lblSubTotalVenta.setText("$ " + String.valueOf(subTotalVenta));
        descuentoPorcentaje = Float.parseFloat(tfDescuentoPorcentaje.getText());
        descuentoEfectivo = Float.parseFloat(tfDescuentoEfectivo.getText());
        totalVenta = (subTotalVenta - descuentoEfectivo) * (1 - descuentoPorcentaje/100);
        lblTotalVenta.setText("$ " + String.format("%.2f", totalVenta));
    }
    
    public void tfDescuentoPorcentaje_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Float cantidad = Float.parseFloat(tfDescuentoPorcentaje.getText());
            } catch (NumberFormatException exc) {
                tfDescuentoPorcentaje.setText("0");
            } finally {
                calcularTotalVenta();
            }
        }
    }
    
    public void tfDescuentoEfectivo_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Float cantidad = Float.parseFloat(tfDescuentoEfectivo.getText());
            } catch (NumberFormatException exc) {
                tfDescuentoEfectivo.setText("0");
            } finally {
                calcularTotalVenta();
            }
        }
    }
    
    public void guardarVentaEfectivo() {
        if (totalVenta == 0f) {
            return;
        }
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogVentaEfectivo.fxml"));
            Parent root = (Parent)loader.load();
            DialogVentaEfectivoController controller = loader.<DialogVentaEfectivoController>getController();
            controller.setParent(this, totalVenta);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception exc) {
            
        }
    }    
    
    public void guardarVentaTarjeta() {
        if (totalVenta == 0f) {
            return;
        }
        guardarVenta("TARJETA", 0f);
    }
    
    public void guardarVenta(String tipo, Float cambio) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();            
            String fecha = dateFormat.format(cal.getTime());            
            Venta v = Venta.guardarVenta(fecha, subTotalVenta, totalVenta, descuentoPorcentaje, descuentoEfectivo, "ACTIVA", tipo, cambio, detalleVenta, tfVendedor.getText());
            Reporte r = new Reporte();
            r.setVentaActual(v);
            if (cbImprimir.selectedProperty().getValue()) {
                r.crearTicketImprimir();
            } else {
                r.crearTicket();
            }
            Stage stage = (Stage) tfBusquedaCodigo.getScene().getWindow();
            stage.close();
        } catch (Exception exc) {
            
        }
    }
    
    public void nuevoApartado() {
        if (totalVenta == 0f) {
            return;
        }
        try {
            Stage stage = new Stage();        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoApartado.fxml"));
            Parent root = (Parent)loader.load();
            NuevoApartadoController controller = loader.<NuevoApartadoController>getController();
            controller.setParent(this, totalVenta);            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception exc) {
            
        }
    }
    
    public void guardarApartado(String tipo, Float restan, String cliente, Float abono) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();            
            String fecha = dateFormat.format(cal.getTime());            
            Apartado a = Apartado.guardarApartado(fecha, subTotalVenta, totalVenta, descuentoPorcentaje, descuentoEfectivo, "ACTIVA", tipo, restan, detalleVenta, tfVendedor.getText(), cliente, abono);
            Reporte r = new Reporte();
            r.setApartadoActual(a);
            if (cbImprimir.selectedProperty().getValue()) {
               r.crearApartadoImprimir();
               r.crearApartadoAbonosImprimir();
            } else {
                //r.crearTicket();
            }
            Stage stage = (Stage) tfBusquedaCodigo.getScene().getWindow();
            stage.close();
        } catch (Exception exc) {
            
        }
    }
}